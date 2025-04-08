package com.tinuproject.tinu.s3.dto.request

data class S3VerifiableRequest (
    val key: String,
    val ETag: String
)