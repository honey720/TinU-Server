package com.tinuproject.tinu.annotation.sample

import com.fasterxml.jackson.databind.ObjectMapper
import com.tinuproject.tinu.annotation.MockControllerTest
import com.tinuproject.tinu.domain.member.controller.RefreshTokenController
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@MockControllerTest(controllers = [RefreshTokenController::class])
class ControllerSampleTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @MockBean
    private val refreshTokenController: RefreshTokenController? = null

    @Test
    @DisplayName("sampleTest")
    fun Test() {
        //Given

        //When

        //Then
    }
}