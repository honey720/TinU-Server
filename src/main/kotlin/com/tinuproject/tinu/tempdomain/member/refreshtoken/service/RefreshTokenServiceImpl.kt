package com.tinuproject.tinu.tempdomain.member.refreshtoken.service

import com.tinuproject.tinu.tempdomain.member.refreshtoken.entity.RefreshToken
import com.tinuproject.tinu.tempdomain.member.refreshtoken.exception.NotFoundTokenException
import com.tinuproject.tinu.tempdomain.member.repository.MemberRepository
import com.tinuproject.tinu.tempdomain.member.refreshtoken.dto.output.Tokens
import com.tinuproject.tinu.tempdomain.member.refreshtoken.repository.RefreshTokenRepository
import com.tinuproject.tinu.security.jwt.JwtUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class RefreshTokenServiceImpl(
    @Value("\${jwt.refresh-token.expiration-time}")
    private val REFRESH_TOKEN_EXPIRATION_TIME: Long, // 리프레쉬 토큰 유효기간

    @Value("\${jwt.access-token.expiration-time}")
    private val ACCESS_TOKEN_EXPIRATION_TIME: Long, // 액세스 토큰 유효기간

    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtUtil : JwtUtil,
    private val memberRepository: MemberRepository
): RefreshTokenService {
    var log : Logger = LoggerFactory.getLogger(this::class.java)

    override fun reissueAccessTokenByRefreshToken(refreshToken: String): Tokens {
        jwtUtil.validateToken(refreshToken)

        if(refreshTokenRepository.findByToken(refreshToken)==null){
            throw NotFoundTokenException()
        }

        val userId :UUID = UUID.fromString(jwtUtil.getUserIdFromToken(refreshToken))

        //리프레쉬 토큰 삭제
        refreshTokenRepository.deleteByUserId(userId)

        //리프레쉬 토큰 재발행.
        val token = jwtUtil.generateRefreshToken(userId,REFRESH_TOKEN_EXPIRATION_TIME)

        val newRefreshToken  = RefreshToken(userId = userId, token = token)

        //리프레쉬 토큰 저장
        refreshTokenRepository.save(newRefreshToken)

        //AccesToken 재발행.
        val accessToken : String = jwtUtil.generateAccessToken(userId, ACCESS_TOKEN_EXPIRATION_TIME,memberRepository.existsByUserId(userId = userId))

        return Tokens(accessToken=accessToken, refreshToken = refreshToken)
    }

    override fun deleteRefreshToken(refreshToken: String) {
        jwtUtil.validateToken(refreshToken)

        val userId : UUID = UUID.fromString(jwtUtil.getUserIdFromToken(refreshToken))

        refreshTokenRepository.deleteByUserId(userId)
    }
}