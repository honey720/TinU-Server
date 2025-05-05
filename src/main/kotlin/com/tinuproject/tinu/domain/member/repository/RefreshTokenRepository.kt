package com.tinuproject.tinu.domain.member.repository

import com.tinuproject.tinu.domain.member.entity.RefreshToken
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RefreshTokenRepository:CrudRepository<RefreshToken, Long> {

    fun findByToken(token : String) : RefreshToken?

    @Transactional
    @Modifying
    fun deleteByUserId(userId : UUID)


    @Transactional
    @Modifying
    fun deleteByToken(token : String)
}