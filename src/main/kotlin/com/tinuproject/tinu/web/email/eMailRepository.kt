package com.tinuproject.tinu.web.email

import com.tinuproject.tinu.web.email.entity.EMailAuth
import org.springframework.data.repository.CrudRepository
import java.util.*

interface EMailRepository : CrudRepository<EMailAuth,Long> {
    fun findByUserId(userId : String) : EMailAuth?
}