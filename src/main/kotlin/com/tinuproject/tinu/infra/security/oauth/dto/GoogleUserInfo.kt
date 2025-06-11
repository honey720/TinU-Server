package com.tinuproject.tinu.infra.security.oauth.dto

class GoogleUserInfo(
    private var attributes: Map<String, Any>
) : OAuth2UserInfoDto{

    override fun getProviderId(): String {
        return attributes["sub"] as String
    }

    override fun getProvider(): String {
        return "google"
    }

    override fun getName(): String {
        return attributes["name"] as String
    }
}