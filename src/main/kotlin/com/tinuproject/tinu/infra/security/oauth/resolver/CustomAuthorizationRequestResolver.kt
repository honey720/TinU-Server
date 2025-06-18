package com.tinuproject.tinu.infra.security.oauth.resolver

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class CustomAuthorizationRequestResolver(
    clientRegisterRepository: ClientRegistrationRepository
) : OAuth2AuthorizationRequestResolver{
    val defaultResolver = DefaultOAuth2AuthorizationRequestResolver(
        clientRegisterRepository,
        OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
    )

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest {
        val resolved = defaultResolver.resolve(request)?: throw Exception("소셜 로그인 실패")

        val registrationId = request.getParameter("registrationId") ?: extractRegistrationId(request)

        return if (registrationId=="apple"){
            OAuth2AuthorizationRequest.from(resolved)
                .additionalParameters { it["response_mod"] = "form_post" }
                .build()
        }else{
            resolved
        }
    }

    override fun resolve(request: HttpServletRequest?, clientRegistrationId: String?): OAuth2AuthorizationRequest {
        val resolved = defaultResolver.resolve(request, clientRegistrationId) ?: throw Exception("소셜 로그인 실패")

        return if (clientRegistrationId=="apple"){
            OAuth2AuthorizationRequest.from(resolved)
                .additionalParameters { it["response_mod"] = "form_post" }
                .build()
        }else{
            resolved
        }
    }


    private fun extractRegistrationId(request: HttpServletRequest) : String{
        val uri = request.requestURI
        return uri.substringAfterLast("/")
    }
}