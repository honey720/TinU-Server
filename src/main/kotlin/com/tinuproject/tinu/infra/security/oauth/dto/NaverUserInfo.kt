package com.tinuproject.tinu.infra.security.oauth.dto

class NaverUserInfo(
    private var attributes: Map<String, Any>
): OAuth2UserInfoDto {
    override fun getProviderId(): String {
        return attributes["id"].toString()
    }

    override fun getProvider(): String {
        return "naver"
    }

    override fun getName() : String {
        return attributes["name"].toString()
    }
}