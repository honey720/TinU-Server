package com.tinuproject.tinu.s3.dto.response

import java.time.LocalDateTime

class S3PresignedUrlResponse(
        val presignedUrls: MutableList<String>,
        val expiration: LocalDateTime
)