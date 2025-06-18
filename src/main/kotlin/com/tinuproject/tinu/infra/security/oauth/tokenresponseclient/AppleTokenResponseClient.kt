package com.tinuproject.tinu.infra.security.oauth.tokenresponseclient

import com.tinuproject.tinu.global.response.ResponseEntityGenerator
import com.tinuproject.tinu.infra.security.jwt.AppleJwtGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange



class AppleTokenResponseClient(
    //Kotlin은 자바와 달리 함수형 매개변수를 Class에서도 사용 가능.
    private val generator : () -> String
) : OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>{

    private val restTemplate = RestTemplate()

    val log : Logger = LoggerFactory.getLogger(this::class.java)
    override fun getTokenResponse(request: OAuth2AuthorizationCodeGrantRequest): OAuth2AccessTokenResponse {
        val clientRegistration = request.clientRegistration
        val redirectUri = request.authorizationExchange.authorizationRequest.redirectUri
        val code = request.authorizationExchange.authorizationResponse.code

        val jwtToken = generator()

        val formData = LinkedMultiValueMap<String, String>().apply {
            add("client_id", clientRegistration.clientId)
            add("client_secret",jwtToken)
            add("code",code)
            add("grant_type", "authorization_code")
            add("redirect_uri", redirectUri)
        }


        val headers = HttpHeaders().apply {
            contentType =  MediaType.APPLICATION_FORM_URLENCODED
        }

        val entity = HttpEntity(formData, headers)


        val response = restTemplate.exchange(
            clientRegistration.providerDetails.tokenUri,
            HttpMethod.POST,
            entity,
            object : ParameterizedTypeReference<Map<String, Any>>(){}
        ).body ?: throw Exception("소셜 로그인 실패 Exception")

        val accessToken = response["access_token"] as String
        val refreshToken = response["refresh_token"] as? String
        val expiresIn = (response["expires_in"]as Number).toLong()


        return OAuth2AccessTokenResponse.withToken(accessToken)
            .tokenType(OAuth2AccessToken.TokenType.BEARER)
            .expiresIn(expiresIn)
            .refreshToken(refreshToken)
            .scopes(clientRegistration.scopes)
            //해당 response에 id_Token이 저장되어 있음.
            .additionalParameters(response)
            .build()
    }
}