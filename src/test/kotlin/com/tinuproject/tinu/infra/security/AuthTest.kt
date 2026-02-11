package com.tinuproject.tinu.infra.security

import com.tinuproject.tinu.factory.JwtTestFactory
import com.tinuproject.tinu.infra.security.jwt.JwtUtil
import jakarta.servlet.http.Cookie
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.test.web.servlet.MockMvc
import kotlin.test.Test
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import org.springframework.test.web.servlet.get


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Import(AuthTest.TestControllerConfig::class)
class AuthTest (

    @Autowired
    private val mockMvc: MockMvc,

    @Autowired
    private val jwtTokenFactory:JwtTestFactory
)
{

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @TestConfiguration
    class TestControllerConfig {

        /**
         * =========================
         * 테스트 전용 더미 Controller
         * =========================
         */
        @RestController
        internal class TestAuthController {

            @GetMapping("/test/permit-all")
            fun permitAll(): String {
                return "OK"
            }

            // 인증만 되면 OK
            @GetMapping("/test/authenticated")
            fun authenticated(@AuthenticationPrincipal userId: UUID): String {
                return userId.toString()
            }

            // USER만 OK
            @GetMapping("/test/user")
            fun user(@AuthenticationPrincipal userId: UUID): String {
                return "OK"
            }
        }
    }
    /**
     * =========================
     * 테스트 케이스
     * =========================
     */
    @Test
    fun `permitAll API는 토큰 없이 접근 가능`() {
        mockMvc.get("/test/permit-all")
            .andExpect {
                status { isOk() }
                content { string("OK") }
            }
    }

    @Test
    fun `authenticated API는 토큰이 없으면 401`() {
        mockMvc.get("/test/authenticated")
            .andExpect {
                status { isUnauthorized() }
            }
    }


    @Test
    fun `authenticated API는 invalid secret 토큰이면 401`() {
        val token = jwtTokenFactory.generateInvalidSecretKey(
            uuid = UUID.randomUUID(),
            expirationMillis = 60_000,
            isSign = true
        )

        mockMvc.get("/test/authenticated") {
            cookie(Cookie("access-token", token))
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `authenticated API는 Expired  토큰이면 401`() {
        val token = jwtUtil.generateAccessToken(
            uuid = UUID.randomUUID(),
            expirationMillis = -1,
            isSign = true
        )

        mockMvc.get("/test/authenticated") {
            cookie(Cookie("access-token", token))
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `authenticated API는 userId 없는 토큰이면 401`() {
        val token = jwtTokenFactory.generateInvalidUserID(
            expirationMillis = 60_000,
            isSign = true
        )

        mockMvc.get("/test/authenticated") {
            cookie(Cookie("access-token", token))
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `authenticated API는 isSign 없는 토큰이면 401`() {
        val token = jwtTokenFactory.generateInvalidIsSign(
            uuid = UUID.randomUUID(),
            expirationMillis = 60_000
        )

        mockMvc.get("/test/authenticated") {
            cookie(Cookie("access-token", token))
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `authenticated API는 정상 토큰이면 접근 가능`() {
        val token = jwtTokenFactory.generateAccessToken(isSign = true)

        mockMvc.get("/test/authenticated") {
            cookie(Cookie("access-token", token))
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `user API는 인증된 사용자면 접근 가능`() {
        val token = jwtTokenFactory.generateAccessToken(isSign = true)

        mockMvc.get("/test/user") {
            cookie(Cookie("access-token", token))
        }.andExpect {
            status { isOk() }
            content { string("OK") }
        }
    }
    @Test
    fun `hasRole(user) API에 isSign이 false 인 Token으로 요청이 올 경우 401 예외 반환`() {
        val token = jwtTokenFactory.generateAccessToken(isSign = false)

        mockMvc.get("/test/user") {
            cookie(Cookie("access-token", token))
        }.andExpect {
            status { isUnauthorized() }
        }
    }


    @Test
    fun `user API는 토큰이 없으면 401`() {
        mockMvc.get("/test/user")
            .andExpect {
                status { isUnauthorized() }
            }
    }

}