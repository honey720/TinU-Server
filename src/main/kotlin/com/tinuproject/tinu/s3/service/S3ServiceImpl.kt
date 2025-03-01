package com.tinuproject.tinu.s3.service

import com.tinuproject.tinu.domain.exception.s3.*
import com.tinuproject.tinu.domain.exception.s3.NoSuchKeyException
import com.tinuproject.tinu.s3.dto.request.*
import com.tinuproject.tinu.s3.dto.response.S3PresignedUrlResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class S3ServiceImpl(
        @Value("\${aws.s3.bucket}")
        private val bucketName: String,
        private val s3Client: S3Client,
        private val s3Presigner: S3Presigner,
        @Value("\${cloudfront.domain}")
        private var cloudFrontDomain: String
) : S3Service {
    override fun getPreSignedUrl(s3PresignedUrlRequest: S3PresignedUrlRequest): S3PresignedUrlResponse {

        val contents = s3PresignedUrlRequest.contents
        if (contents.size !in 1..10)
            throw UploadSizeOutOfRangeException()

        contents.map { content ->
            if (content.contentType !in ALLOWED_EXTENSIONS)
                throw NotAllowedExtensionException()
            if (content.contentLength !in 1..(1024 * 1024 * 10))
                throw FileLengthOutOfRange()
        }

        val currentTimeMillis = System.currentTimeMillis()
        val uuid = UUID.randomUUID()
        val expiration = Duration.ofMinutes(10)

        //각 파일에 대한 Presigned URL 생성
        val objects = contents.mapIndexed() { index, content ->
            val extension = content.contentType.split("/")[1]
            val key = "original/${currentTimeMillis}_${uuid}_${index}.${extension}"

            val presignedPutObjectRequest: PresignedPutObjectRequest = s3Presigner.presignPutObject(PutObjectPresignRequest.builder()
                    .signatureDuration(expiration)
                    .putObjectRequest {
                        it.bucket(bucketName)
                                .contentType(content.contentType)
                                .contentLength(content.contentLength)
                                .key(key)
                    }
                    .build()
            )

            S3PresignedUrlResponse.Object(
                    presignedUrl = presignedPutObjectRequest.url().toString(),
                    key = key
            )
        }.toMutableList()

        //응답 DTO 생성 및 반환
        return S3PresignedUrlResponse(
                objects = objects,
                expiration = LocalDateTime.now().plus(expiration)
        )
    }

    companion object {
        private val ALLOWED_EXTENSIONS = listOf("image/jpg", "image/jpeg", "image/png", "image/webp")
    }

    override fun verifyImage(objects: MutableList<Object>): MutableList<String> {
        val urls = objects.map { obj ->
            val response: HeadObjectResponse
            try {
                response = s3Client.headObject(HeadObjectRequest.builder()
                        .bucket(bucketName)
                        .key(obj.key)
                        .build())
            } catch (e: software.amazon.awssdk.services.s3.model.NoSuchKeyException) {
                throw NoSuchKeyException()
            }
            if (response.eTag() != obj.eTag) {
                throw InvalidETagException()
            }

            "${cloudFrontDomain}/${obj.key}"
        }.toMutableList()

        return urls
    }

    data class Object(
            val key: String,
            val eTag: String
    )
}