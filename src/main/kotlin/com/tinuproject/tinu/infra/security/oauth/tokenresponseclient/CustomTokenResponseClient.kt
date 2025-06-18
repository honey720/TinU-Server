package com.tinuproject.tinu.infra.security.oauth.tokenresponseclient

import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse

class CustomTokenResponseClient(
    private val appleTokenResponseClient: OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>,
    private val defaultClient : OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
) : OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>{
    override fun getTokenResponse(authorizationGrantRequest: OAuth2AuthorizationCodeGrantRequest): OAuth2AccessTokenResponse {
        val registrationId = authorizationGrantRequest.clientRegistration.registrationId


        return if ( registrationId == "apple"){
            appleTokenResponseClient.getTokenResponse(authorizationGrantRequest)
        }else{
            defaultClient.getTokenResponse(authorizationGrantRequest)
        }
    }


}