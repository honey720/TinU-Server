package com.tinuproject.tinu.tempdomain.member.refreshtoken.service

import com.tinuproject.tinu.tempdomain.member.refreshtoken.dto.output.Tokens


interface RefreshTokenService {
    fun reissueAccessTokenByRefreshToken(refreshToken : String) : Tokens

    fun deleteRefreshToken(refreshToken: String)
}