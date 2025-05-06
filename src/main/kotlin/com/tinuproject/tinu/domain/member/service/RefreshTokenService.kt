package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.service.dto.output.Tokens


interface RefreshTokenService {
    fun reissueAccessTokenByRefreshToken(refreshToken : String) : Tokens

    fun deleteRefreshToken(refreshToken: String)
}