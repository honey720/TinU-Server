package com.tinuproject.tinu.domain.member.dto.client_controller.request

data class UpdateUserInfoRequestDTO(
    val name : String,
    val introduction : String,
    val major : String,
    val grade : Int
) {
}