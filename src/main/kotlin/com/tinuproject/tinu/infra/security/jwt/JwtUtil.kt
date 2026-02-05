package com.tinuproject.tinu.infra.security.jwt

import com.tinuproject.tinu.domain.member.exception.ExpiredTokenException
import com.tinuproject.tinu.domain.member.exception.InvalidedTokenException
import com.tinuproject.tinu.domain.member.exception.NotFoundTokenException
import com.tinuproject.tinu.domain.member.exception.UnknownTokenException
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.SignatureException
import java.util.*
import javax.crypto.SecretKey
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders


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
    fun generateRefreshToken(uuid : UUID, expirationMillis: Long): String {
        log.info("리프레쉬 토큰 발행.")
        return Jwts.builder()
            .claim("userId", uuid.toString()) // 클레임에 userId 추가
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMillis))
            .signWith(getSigningKey(REFRESH_TOKEN_SECRET))
            .compact()
    }

    //AccessToken이 유효한지 확인
    fun validateAccessToken(token : String): Jws<Claims>{
        //parseClamisJWS에서 발생하는 예외를
        //본 프로젝트에서의 예외로 변경.
        try{
            return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(ACCESS_TOKEN_SECRET))
                .build()
                .parseClaimsJws(token)
        }catch (e : SignatureException){
            throw InvalidedTokenException()
        }catch (e : ExpiredJwtException){
            throw ExpiredTokenException()
        }catch (e : JwtException){
            throw UnknownTokenException()
        }catch (e : Exception){
            throw Exception()
        }
    }

    //AccessToken이 유효한지 확인
    fun validateRefreshToken(token : String): Jws<Claims> {
        //parseClamisJWS에서 발생하는 예외를
        //본 프로젝트에서의 예외로 변경.
        try{
            return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(REFRESH_TOKEN_SECRET))
                .build()
                .parseClaimsJws(token)
        }catch (e : SignatureException){
            throw InvalidedTokenException()
        }catch (e : ExpiredJwtException){
            throw ExpiredTokenException()
        }catch (e : JwtException){
            throw UnknownTokenException()
        }catch (e : Exception){
            throw Exception()
        }
    }


    //Token 에서 claim 반환하는 메서드
    fun getClaimsFromAccessToken(token : String) : Claims{
        return validateAccessToken(token).body
    }


    // 토큰에서 유저 id를 반환하는 메서드
    fun getUserIdFromToken(token: String): String {
        return getClaimsFromAccessToken(token)["userId"].toString()
    }

    // 토큰에서 isSing 정보 추출
    fun signCheck(token : String) :Boolean{
        return getClaimsFromAccessToken(token)["isSign"].toString().toBoolean()
    }



}