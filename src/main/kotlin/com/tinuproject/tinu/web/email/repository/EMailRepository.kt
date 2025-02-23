package com.tinuproject.tinu.web.email.repository

import com.tinuproject.tinu.web.email.entity.EMailAuth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.*

interface EMailRepository : JpaRepository<EMailAuth, Long> {
    fun findByUserId(userId : UUID) : EMailAuth?

    fun findByEmail(email: String) : EMailAuth?
}