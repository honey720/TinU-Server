package com.tinuproject.tinu.domain.member.repository

import com.tinuproject.tinu.domain.member.entity.RefreshToken
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.*

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RefreshTokenRepositoryTest {

    @Autowired
    private lateinit var refreshTokenRepository : RefreshTokenRepository

    @Test
    @DisplayName("123123")
    fun Test(){
        //Given
        val userId =  UUID.randomUUID();
        val token = UUID.randomUUID().toString();
        val refreshToken = RefreshToken(userId = userId, token = token)

        refreshTokenRepository.save(refreshToken);
        //When

        //Then
    }
}