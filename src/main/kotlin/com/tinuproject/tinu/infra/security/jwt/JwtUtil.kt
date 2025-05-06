package com.tinuproject.tinu.infra.security.jwt

import com.tinuproject.tinu.domain.member.exception.ExpiredTokenException
import com.tinuproject.tinu.domain.member.exception.InvalidedTokenException
import com.tinuproject.tinu.domain.member.exception.NotFoundTokenException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.SignatureException
import java.util.*
import javax.crypto.SecretKey
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders


@Component
class JwtUtil {
    var log : Logger = LoggerFactory.getLogger(this::class.java)
    @Value("\${jwt.secret}")
    lateinit var SECRET_KEY : String

    private fun getSigningKey(): SecretKey {
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
            .signWith(getSigningKey())
            .compact()
    }

    // 리프레쉬 토큰을 발급하는 메서드
    fun generateRefreshToken(uuid : UUID, expirationMillis: Long): String {
        log.info("리프레쉬 토큰 발행.")
        return Jwts.builder()
            .claim("userId", uuid.toString()) // 클레임에 userId 추가
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMillis))
            .signWith(getSigningKey())
            .compact()
    }

    // 응답 헤더에서 액세스 토큰을 반환하는 메서드
    fun getTokenFromHeader(httpServletRequest: HttpServletRequest): String {
        val authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)
        //hasText(token)토큰이 넘어왔는지 확인
        if(StringUtils.hasText(authorizationHeader))
            return authorizationHeader.substring(7)
        else
            throw NotFoundTokenException()
    }

    // 토큰에서 유저 id를 반환하는 메서드
    fun getUserIdFromToken(token: String?): String {
        return try {
            val userId: String = getClaimsFromToken(token)
                .get("userId", String::class.java)
            log.info("유저 id 반환")
            userId
        } catch (e: JwtException) {
            // 토큰이 유효하지 않은 경우
            log.warn("유효하지 않은 토큰입니다.")
            //(토큰이 유효하지 않는 경우 반환하는 Exception을 만들어 처리)
            throw InvalidedTokenException()
        } catch (e: IllegalArgumentException) {
            log.warn("유효하지 않은 토큰입니다.")
            //(토큰이 유효하지 않는 경우 반환하는 Exception을 만들어 처리)
            throw InvalidedTokenException()
        }
    }

    //Token 에서 claim 반환하는 메서드
    fun getClaimsFromToken(token : String?) : Claims{
        try{
            return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .body
        }catch(e : SignatureException){
            log.warn("Claim이 유효하지 않은 토큰입니다.{}",e.message)
            throw e
        }catch(e : ExpiredJwtException){
            log.warn("토큰이 만료되었습니다.")
            throw e
        }
        catch (e : Exception){
            log.warn("토큰과 관련한 예기치 못한 에러가 발생했습니다{}.",e.message)
            throw Exception()
        }
    }

    //토큰이 유효한지 확인
    fun validateToken(token : String){
        //parseClamisJWS에서 발생하는 예외를
        //본 프로젝트에서의 예외로 변경.
        try{
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
        }catch (e : SignatureException){
            throw InvalidedTokenException()
        }catch (e : ExpiredJwtException){
            throw ExpiredTokenException()
        }catch (e : Exception){
            throw Exception()
        }
    }

    fun signCheck(token : String) :Boolean{
        return try {
            val isSign : Boolean = getClaimsFromToken(token)["isSign"].toString().toBoolean()


            isSign
        } catch (e: JwtException) {
            // 토큰이 유효하지 않은 경우
            log.warn("유효하지 않은 토큰입니다.")
            //(토큰이 유효하지 않는 경우 반환하는 Exception을 만들어 처리)
            throw InvalidedTokenException()
        } catch (e: IllegalArgumentException) {
            log.warn("유효하지 않은 토큰입니다.")
            //(토큰이 유효하지 않는 경우 반환하는 Exception을 만들어 처리)
            throw InvalidedTokenException()
        }
    }



}