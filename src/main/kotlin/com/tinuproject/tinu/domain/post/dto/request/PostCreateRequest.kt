package com.tinuproject.tinu.domain.post.dto.request

import com.tinuproject.tinu.domain.enums.PaymentMethod
import com.tinuproject.tinu.domain.enums.SellMethod
import com.tinuproject.tinu.s3.dto.request.S3VerifiableRequest

data class PostCreateRequest(
        val title: String,
        val body: String,
        val categoryId: Long,
        val price: Int,
        val sellMethod: Set<SellMethod> = setOf(),
        val paymentMethod: Set<PaymentMethod> = setOf(),
        val images: List<S3VerifiableRequest>,
        val hashTag: List<String>
)