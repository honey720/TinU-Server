package com.tinuproject.tinu.s3.service

import com.tinuproject.tinu.domain.exception.s3.UploadOutOfRangeException
import com.tinuproject.tinu.s3.dto.request.*
import com.tinuproject.tinu.s3.dto.response.S3PresignedUrlResponse
import com.tinuproject.tinu.s3.dto.response.S3UploadCompleteResponse
import com.tinuproject.tinu.s3.dto.response.S3UploadInitiateResponse
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
        //jwt 정보도 추가
) : S3Service {

    override fun initiateUpload(s3UploadInitiateRequest: S3UploadInitiateRequest): S3UploadInitiateResponse {

        //S3에 저장할 객체 키 생성 (현재 시간 + 원본 파일 이름)
        val targetObjectDir = "original/${System.currentTimeMillis()}_${s3UploadInitiateRequest.originalFileName}"

        //S3에 멀티파트 업로드를 요청할 양식을 작성
        val createMultipartUploadRequest: CreateMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(targetObjectDir)
                .build()

        //S3에 멀티파트 업로드 초기화 요청
        val createMultipartUploadResponse: CreateMultipartUploadResponse = s3Client.createMultipartUpload(createMultipartUploadRequest)

        //응답 DTO 생성 및 반환
        return S3UploadInitiateResponse(
                uploadId = createMultipartUploadResponse.uploadId(),
                key = targetObjectDir
        )
    }

    override fun getPreSignedUrl(s3PresignedUrlRequest: S3PresignedUrlRequest): S3PresignedUrlResponse {

        //업로드할 파일의 갯수가 1개 이상 10개 이하인지 확인
        if (s3PresignedUrlRequest.size !in 1..10)
            throw UploadOutOfRangeException()

        //Presigned URL 만료 시간 설정
        val expiration = Duration.ofMinutes(2)

        //Presigned URL 생성
        val presignedUrls = (1..s3PresignedUrlRequest.size).map { index ->
            val key = "original/${System.currentTimeMillis()}_${UUID.randomUUID()}_$index"
            val presignedPutObjectRequest: PresignedPutObjectRequest = s3Presigner.presignPutObject(PutObjectPresignRequest.builder()
                    .signatureDuration(expiration)
                    .putObjectRequest {
                        it.bucket(bucketName)
                                .key(key)
                    }
                    .build()
            )
            presignedPutObjectRequest.url().toString()
        }.toMutableList()

        //응답 DTO 생성 및 반환
        return S3PresignedUrlResponse(
                presignedUrls = presignedUrls,
                expiration = LocalDateTime.now().plus(expiration)
        )
    }

    override fun completeUpload(s3UploadCompleteRequest: S3UploadCompleteRequest): S3UploadCompleteResponse {
        //하나의 컨텐츠에 대한 모든 부분들에 partNumber와 Etag를 설정함
        val completedPart = s3UploadCompleteRequest.parts.map { partForm ->
            CompletedPart.builder()
                    .partNumber(partForm.partNumber)
                    .eTag(partForm.eTag)
                    .build()
        }

        //멀티파트 업로드 완료 요청할 양식중 멀티파트에 해당하는 양식을 작성
        val completedMultipartUpload: CompletedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(completedPart)
                .build()

        //멀티파트 업로드 완료 요청할 양식을 작성
        val completeMultipartUploadRequest: CompleteMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(s3UploadCompleteRequest.key)
                .uploadId(s3UploadCompleteRequest.uploadId)
                .multipartUpload(completedMultipartUpload)
                .build()

        //멀티파트 업로드 완료 요청
        val completeMultipartUploadResponse: CompleteMultipartUploadResponse = s3Client.completeMultipartUpload(completeMultipartUploadRequest)

        val objectKey: String = completeMultipartUploadResponse.key()
        val url: String = cloudFrontDomain + "/" + objectKey
        val bucket: String = completeMultipartUploadResponse.bucket()
        val fileSize: Long = getFileSizeFromS3Url(bucket, objectKey)

        //응답 DTO 생성 및 반환
        return S3UploadCompleteResponse(
                fileName = objectKey,
                url = url,
                fileSize = fileSize
        )
    }

    override fun abortUpload(s3UploadAbortRequest: S3UploadAbortRequest) {
        //멀티파트 업로드 취소 요청할 양식을 작성
        val abortMultipartUploadRequest: AbortMultipartUploadRequest = AbortMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(s3UploadAbortRequest.key)
                .uploadId(s3UploadAbortRequest.uploadId)
                .build()

        //멀티파트 업로드 취소 요청
        s3Client.abortMultipartUpload(abortMultipartUploadRequest)
    }

    override fun deleteObject(s3DeleteRequest: S3DeleteRequest) {

        //S3 객체 url에서 key 추출
        val objectName: String = s3DeleteRequest.url.split("/").drop(3).joinToString("/")

        //S3 객체 삭제 요청을 위한 양식을 작성
        val deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build()

        //S3 객체 삭제 요청
        s3Client.deleteObject(deleteObjectRequest)
    }

    override fun getFileSizeFromS3Url(bucketName: String, fileName: String): Long {
        val metadataRequest: HeadObjectRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()
        return s3Client.headObject(metadataRequest).contentLength()
    }

}