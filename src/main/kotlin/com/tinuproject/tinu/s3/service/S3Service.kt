package com.tinuproject.tinu.s3.service

import com.tinuproject.tinu.s3.dto.S3Verifiable
import com.tinuproject.tinu.s3.dto.request.*
import com.tinuproject.tinu.s3.dto.response.S3PresignedUrlResponse

interface S3Service {
    suspend fun getPreSignedUrl(s3PresignedUrlRequest: S3PresignedUrlRequest): S3PresignedUrlResponse

    suspend fun verifyImage(objects: List<S3Verifiable>): MutableList<String>

    suspend fun removeImage(objects: MutableList<String>)

}