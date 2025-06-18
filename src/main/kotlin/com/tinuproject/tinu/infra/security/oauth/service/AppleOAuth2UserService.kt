package com.tinuproject.tinu.infra.security.oauth.service

import com.tinuproject.tinu.infra.security.config.AppleProperties
import io.jsonwebtoken.Jwts
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.security.PublicKey
import java.util.*
import org.json.JSONObject
import java.math.BigInteger
import java.net.URL
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import kotlin.math.exp

@Component
class AppleOAuth2UserService(
    val appleProperties: AppleProperties
) {

    fun appleLoadUser(userRequest : OAuth2UserRequest?) : OAuth2User{
        val idToken = userRequest!!.additionalParameters["id_token"] as? String
            ?: throw Exception("소셜 로그인 실패")

        val claims = parseIdToken(idToken)

        val attributes = mapOf(
          appleProperties.userNameAttribute to claims[appleProperties.userNameAttribute]
        )

        return DefaultOAuth2User(
            listOf(SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            appleProperties.userNameAttribute
        )
    }

    private fun parseIdToken(idToken:String) : Map<String, Any>{

        val parser = Jwts.parserBuilder().setSigningKey(getApplePublicKey(idToken)).build()

        val jwt = parser.parseClaimsJws(idToken)

        return jwt.body
    }

    private fun getApplePublicKey(idToken:String) : PublicKey{
        val parts = idToken.split(".")

        val headerJson = String(Base64.getUrlDecoder().decode(parts[0]))

        val header = JSONObject(headerJson)

        val kid = header.getString("kid")

        val jwksUrl = URL(appleProperties.jwkUrl)

        val jwks = JSONObject(jwksUrl.readText())

        val keys = jwks.getJSONArray("keys")

        for(i in 0 until keys.length()){
            val key = keys.getJSONObject(i)
            if(key.getString("kid")==kid){
                val n = key.getString("n")
                val e = key.getString("e")

                val modulus = BigInteger(1,Base64.getUrlDecoder().decode(n))

                val exponent = BigInteger(1, Base64.getUrlDecoder().decode(e))

                val keySpec = RSAPublicKeySpec(modulus, exponent)

                return KeyFactory.getInstance("RSA").generatePublic(keySpec)
            }
        }

        throw Exception("소셜 로그인 실패.")
    }

}