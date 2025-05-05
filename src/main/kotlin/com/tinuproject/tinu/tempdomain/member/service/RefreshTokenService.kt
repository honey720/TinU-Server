package com.tinuproject.tinu.tempdomain.member.service

import com.tinuproject.tinu.tempdomain.member.service.dto.output.Tokens


interface RefreshTokenService {
    fun reissueAccessTokenByRefreshToken(refreshToken : String) : Tokens

    fun deleteRefreshToken(refreshToken: String)
}