package com.tinuproject.tinu.global.web

import com.tinuproject.tinu.domain.member.exception.NotFoundTokenException
import com.tinuproject.tinu.infra.security.exception.auth.NeedLoginException
import jakarta.servlet.http.HttpServletRequest

object RefreshTokenResolver : TokenResolver {

    private const val REFRESH_TOKEN_COOKIE_NAME = "refresh-token"

    override fun resolve(request: HttpServletRequest): String {
        val cookies = request.cookies ?: throw NotFoundTokenException()

        return cookies
            .firstOrNull { it.name == REFRESH_TOKEN_COOKIE_NAME }
            ?.value
            ?: throw NeedLoginException()
    }
}