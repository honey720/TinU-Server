package com.tinuproject.tinu.domain.member.dto.controller_service.input

import com.tinuproject.tinu.domain.member.dto.client_controller.request.UpdateUserInfoRequestDTO
import com.tinuproject.tinu.domain.member.dto.client_controller.request.UpdateUserInfoRequestDTO.Image
import io.swagger.v3.oas.annotations.media.Schema

class UpdateUserInputDTO(){
    var nickname : String =""
    var introduction : String? = null
    var major : String? = null
    var grade : Int = 4
    var profile : String? = null

    constructor(updateUserInfoRequestDTO: UpdateUserInfoRequestDTO, url : String?):this(){
        this.nickname = updateUserInfoRequestDTO.nickname
        this.grade = updateUserInfoRequestDTO.grade
        this.introduction = updateUserInfoRequestDTO.introduction
        this.major = updateUserInfoRequestDTO.major
        this.profile = url
    }
}
