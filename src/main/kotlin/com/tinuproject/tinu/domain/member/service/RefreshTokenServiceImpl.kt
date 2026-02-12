package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.entity.RefreshToken
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.member.service.dto.output.Tokens
import com.tinuproject.tinu.domain.member.repository.RefreshTokenRepository
import com.tinuproject.tinu.infra.security.exception.auth.NeedLoginException
import com.tinuproject.tinu.infra.security.jwt.JwtUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

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
        jwtUtil.parseRefreshToken(refreshToken)

        val existRefreshToken : RefreshToken = refreshTokenRepository.findByToken(refreshToken)?: throw NeedLoginException()

        val userId = existRefreshToken.userId

        //리프레쉬 토큰 삭제
        refreshTokenRepository.deleteByUserId(userId)

        //리프레쉬 토큰 재발행.
        val reissueRefreshToken = jwtUtil.generateRefreshToken(REFRESH_TOKEN_EXPIRATION_TIME)

        val newRefreshToken  = RefreshToken(userId = userId, token = reissueRefreshToken)

        //리프레쉬 토큰 저장
        refreshTokenRepository.save(newRefreshToken)

        //AccesToken 재발행.
        val accessToken : String = jwtUtil.generateAccessToken(userId, ACCESS_TOKEN_EXPIRATION_TIME,memberRepository.existsByUserId(userId = userId))

        return Tokens(accessToken=accessToken, refreshToken = reissueRefreshToken)
    }

    override fun deleteRefreshToken(refreshToken: String) {
        jwtUtil.parseRefreshToken(refreshToken)

        val existRefreshToken : RefreshToken = refreshTokenRepository.findByToken(refreshToken)?: throw NeedLoginException()

        val userId = existRefreshToken.userId

        refreshTokenRepository.deleteByUserId(userId)
    }
}