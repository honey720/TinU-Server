package com.tinuproject.tinu.s3.service

import com.tinuproject.tinu.s3.dto.request.*
import com.tinuproject.tinu.s3.dto.response.S3PresignedUrlResponse
import com.tinuproject.tinu.s3.dto.response.S3UploadCompleteResponse
import com.tinuproject.tinu.s3.dto.response.S3UploadInitiateResponse

interface S3Service {
    fun initiateUpload(s3UploadInitiateRequest: S3UploadInitiateRequest): S3UploadInitiateResponse

    fun getPreSignedUrl(s3PresignedUrlRequest: S3PresignedUrlRequest): S3PresignedUrlResponse

    fun completeUpload(s3UploadCompleteRequest: S3UploadCompleteRequest): S3UploadCompleteResponse

    fun abortUpload(s3UploadAbortRequest: S3UploadAbortRequest)

    fun deleteObject(s3DeleteRequest: S3DeleteRequest)

    fun getFileSizeFromS3Url(bucketName: String, fileName: String): Long
}