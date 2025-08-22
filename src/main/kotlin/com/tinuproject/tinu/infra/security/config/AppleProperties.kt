package com.tinuproject.tinu.infra.security.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

//Apple social login 관련 properties를 연결하는 클래스
@Configuration
data class AppleProperties(

    //AppleDeveloper(이후 AD)의 service Id
    @Value("\${spring.security.oauth2.client.registration.apple.client-id}")
    val clientId : String,

    //AD에서 발급받은 secret 파일 속 Key
    @Value("\${spring.security.oauth2.client.registration.apple.client-secret}")
    val clientSecret : String,

    //소셜로그인 성공이후 인증 Code를 받아올 RedirectUrl
    @Value("\${spring.security.oauth2.client.registration.apple.redirect-uri}")
    val redirectUrl : String,

    //소셜로그인을 통해 받아올 추가 정보(기본은 AccessToken, RefreshToken, IdToken)
    @Value("\${spring.security.oauth2.client.registration.apple.scope}")
    val scope : String,

    //소셜로그인을 통해 유저 정보를 어떻게 받아올지
    //해당 방식은 로그인 -> 인증 코드 -> 토큰의 과정을 거치는 방식
    @Value("\${spring.security.oauth2.client.registration.apple.authorization-grant-type}")
    val grantType : String,


    //Code를 통해 Token을 받아오는 URI
    @Value("\${spring.security.oauth2.client.provider.apple.token-uri}")
    val tokenUrl : String,

    //token(Json 형태)에서 user 정보가 담겨있는 key
    @Value("\${spring.security.oauth2.client.provider.apple.user-name-attribute}")
    val userNameAttribute : String,

    //AD에서 teamId
    @Value("\${apple.team_id}")
    val teamId : String,

    //AD에서 Service에 대한 Key
    @Value("\${apple.login_key}")
    val serviceKey : String,

    //Apple은 공개 키 방식으로 토큰을 생성
    @Value("\${apple.jwk-key-uri}")
    val jwkUrl : String
)