package com.tinuproject.tinu.infra.security.jwt

import com.tinuproject.tinu.domain.member.exception.ExpiredTokenException
import com.tinuproject.tinu.domain.member.exception.InvalidedTokenException
import com.tinuproject.tinu.domain.member.exception.UnknownTokenException
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*
import javax.crypto.SecretKey
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys


@Component
class JwtUtil(
    @Value("\${jwt.access-token.secret}")
    var ACCESS_TOKEN_SECRET : String,

    @Value("\${jwt.refresh-token.secret}")
    var REFRESH_TOKEN_SECRET : String
) {
    var log : Logger = LoggerFactory.getLogger(this::class.java)

    private fun getSigningKey(SECRET_KEY : String): SecretKey {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    // 액세스 토큰을 발급하는 메서드
    fun generateAccessToken(uuid: UUID, expirationMillis: Long, isSign : Boolean): String {
        log.info("액세스 토큰 발행.")
        return Jwts.builder()
            .claim("userId", uuid.toString())// 클레임에 userId 추가
            .claim("isSign", isSign)// 클레임에 회원가입 여부 추가.
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMillis))
            .signWith(getSigningKey(ACCESS_TOKEN_SECRET))
            .compact()
    }

    // 리프레쉬 토큰을 발급하는 메서드
    fun generateRefreshToken(expirationMillis: Long): String {
        log.info("리프레쉬 토큰 발행.")
        return Jwts.builder()
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMillis))
            .signWith(getSigningKey(REFRESH_TOKEN_SECRET))
            .compact()
    }

    fun validateToken(token : String, secretKey:SecretKey): Jws<Claims>{
        try{
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
        }catch (e : SignatureException){
            log.info("토큰의 형식이 이상합니다.")
            throw InvalidedTokenException()
        }catch (e : ExpiredJwtException){
            log.info("토큰이 만료되었습니다.")
            throw ExpiredTokenException()
        }catch (e : JwtException){
            log.info("토큰에 예기치 못한 문제가 존재합니다.")
            throw UnknownTokenException()
        }
    }

    //AccessToken이 유효한지 확인
    fun parseAccessToken(token : String): Jws<Claims>{
        return validateToken(token, getSigningKey(ACCESS_TOKEN_SECRET))
    }

    //AccessToken이 유효한지 확인
    fun parseRefreshToken(token : String): Jws<Claims> {
        return validateToken(token, getSigningKey(REFRESH_TOKEN_SECRET))
    }


    //Token 에서 claim 반환하는 메서드
    fun getClaimsFromAccessToken(token : String) : Claims{
        return parseAccessToken(token).body
    }


    // 토큰에서 유저 id를 반환하는 메서드
    fun getUserIdFromClaims(claims: Claims): String {

        val userId = claims["userId"]
            ?: throw UnknownTokenException()

        return userId.toString()
    }

    // 토큰에서 isSing 정보 추출
    fun isSigned(claims: Claims): Boolean {

        val isSign = claims["isSign"]
            ?: throw UnknownTokenException()

        return isSign.toString().toBoolean()
    }
}