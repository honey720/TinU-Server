package com.tinuproject.tinu.infra.security.oauth.dto

interface OAuth2UserInfoDto {
    fun getProviderId() : String

    fun getProvider() : String

    fun getName() : String
}