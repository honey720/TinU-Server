package com.tinuproject.tinu.domain.member.dto.input

import com.tinuproject.tinu.domain.member.dto.request.UpdateUserInfoRequestDTO

class UpdateUserInputDTO(updateUserInfoRequestDTO: UpdateUserInfoRequestDTO, url : String?){
    var nickname : String = updateUserInfoRequestDTO.nickname
    var introduction : String? = updateUserInfoRequestDTO.introduction
    var major : String? = updateUserInfoRequestDTO.major
    var grade : Int? = updateUserInfoRequestDTO.grade
    var profile : String? = url
}
