package com.tinuproject.tinu.infra.security.jwt

import com.tinuproject.tinu.infra.security.config.AppleProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.Date.*

@Component
//Apple에서는 code를 통해 Token을 요청할 때 client_secret으로 Jwt토큰 방식을 요구함.
//그렇기에 해당 client_secret에 넣을 토큰을 생성하는 클래스
class AppleJwtGenerator(
    val appleProperties: AppleProperties
) {

    fun generate(): String{
        val now = Instant.now()

        val exp = now.plus(180,ChronoUnit.DAYS)

        return Jwts.builder()
            .setHeaderParam("kid", appleProperties.serviceKey)
            .setIssuer(appleProperties.teamId)
            .setIssuedAt(from(now))
            .setExpiration(from(exp))
            .setAudience("https://appleid.apple.com")
            .setSubject(appleProperties.clientId)
            .signWith(loadPrivateKey(), SignatureAlgorithm.ES256)
            .compact()
    }

    //private key를 받는 방법 2가지
    //제공 받은 privateKey 파일에서 추출
    //privateKey에서 Key 부분만 추출해서 secret에 넣음
    //현재는 secret에서 추출
    private fun loadPrivateKey() : PrivateKey {
        val privateKeyPem = appleProperties.clientSecret
            .replace("-----BEGIN PRIVATE KEY-----","")
            .replace("-----END PRIVATE KEY-----","")
            .replace("\\s+".toRegex(),"")

        val keyBytes = Base64.getDecoder().decode(privateKeyPem)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)

        return KeyFactory.getInstance("EC").generatePrivate(keySpec)
    }
}