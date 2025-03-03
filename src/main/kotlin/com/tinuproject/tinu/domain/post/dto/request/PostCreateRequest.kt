package com.tinuproject.tinu.domain.post.dto.request

import com.tinuproject.tinu.domain.enums.PaymentMethod
import com.tinuproject.tinu.domain.enums.SellMethod

data class PostCreateRequest(
        val title: String,
        val body: String,
        val categoryId: Long,
        val price: Int,
        val sellMethod: SellMethod,
        val paymentMethod: PaymentMethod,
        val images: List<Image>,
        val hashTag: List<String>
) {
    data class Image(
            val key: String,
            val ETag: String
    )
}