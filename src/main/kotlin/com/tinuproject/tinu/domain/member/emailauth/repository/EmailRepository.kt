package com.tinuproject.tinu.domain.member.emailauth.repository

import com.tinuproject.tinu.domain.member.emailauth.entity.EmailAuth
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EmailRepository : JpaRepository<EmailAuth, Long> {
    fun findByUserId(userId : UUID) : EmailAuth?

    fun findByEmail(email: String) : EmailAuth?
}