package com.tinuproject.tinu.infra.security.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


@Configuration
data class AppleProperties(
    @Value("\${spring.security.oauth2.client.registration.apple.client-id}")
    val clientId : String,

    @Value("\${spring.security.oauth2.client.registration.apple.client-secret}")
    val clientSecret : String,

    @Value("\${spring.security.oauth2.client.registration.apple.redirect-uri}")
    val redirectUrl : String,

    @Value("\${spring.security.oauth2.client.registration.apple.scope}")
    val scope : String,

    @Value("\${spring.security.oauth2.client.registration.apple.authorization-grant-type}")
    val grantType : String,

    @Value("\${spring.security.oauth2.client.provider.apple.token-uri}")
    val tokenUrl : String,

    @Value("\${spring.security.oauth2.client.provider.apple.user-info-uri}")
    val  userInfoUrl : String,

    @Value("\${spring.security.oauth2.client.provider.apple.user-name-attribute}")
    val userNameAttribute : String,

    @Value("\${apple.team_id}")
    val teamId : String,

    @Value("\${apple.login_key}")
    val serviceKey : String
)