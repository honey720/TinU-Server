package com.tinuproject.tinu.s3.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class S3VerifiableRequest (
    val key: String,
    @get:JsonProperty("ETag")
    val ETag: String
)