package com.tinuproject.tinu.s3.service

import com.tinuproject.tinu.s3.dto.request.*
import com.tinuproject.tinu.s3.dto.response.S3PresignedUrlResponse
import com.tinuproject.tinu.s3.service.S3ServiceImpl.Object

interface S3Service {
    suspend fun getPreSignedUrl(s3PresignedUrlRequest: S3PresignedUrlRequest): S3PresignedUrlResponse

    fun verifyImage(objects: MutableList<Object>): MutableList<String>

}