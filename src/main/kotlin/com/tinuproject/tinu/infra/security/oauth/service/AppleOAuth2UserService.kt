package com.tinuproject.tinu.infra.security.oauth.service

import com.tinuproject.tinu.infra.security.config.AppleProperties
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class AppleOAuth2UserService(
    val appleProperties: AppleProperties
) {

    fun appleLoadUser(userRequest : OAuth2UserRequest?) : OAuth2User{
        val idToken = userRequest!!.additionalParameters["id_token"] as? String
            ?: throw Exception("소셜 로그인 실패")

        val claims = parseIdToken(idToken)

        val attributes = mapOf(
          appleProperties.userNameAttribute to claims["sub"]
        )

        return DefaultOAuth2User(
            listOf(SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            appleProperties.userNameAttribute
        )
    }


}