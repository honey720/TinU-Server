package com.tinuproject.tinu.domain.member.refreshtoken.service

import com.tinuproject.tinu.domain.member.refreshtoken.dto.output.Tokens


interface RefreshTokenService {
    fun reissueAccessTokenByRefreshToken(refreshToken : String) : Tokens

    fun deleteRefreshToken(refreshToken: String)
}