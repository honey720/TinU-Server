package com.tinuproject.tinu.domain.post.dto.request

import com.tinuproject.tinu.domain.enums.PaymentMethod
import com.tinuproject.tinu.domain.enums.SellMethod
import com.tinuproject.tinu.s3.dto.request.S3VerifiableRequest

data class PostUpdateRequest(
        val title: String,
        val body: String,
        val categoryId: Long,
        val price: Int,
        val sellMethod: SellMethod,
        val paymentMethod: PaymentMethod,
        val images: List<S3VerifiableRequest>,
        val hashTag: List<String>
)