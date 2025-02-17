package com.tinuproject.tinu.web.email.repository

import com.tinuproject.tinu.web.email.entity.EMailAuth
import org.springframework.data.repository.CrudRepository

interface EMailRepository : CrudRepository<EMailAuth,Long> {
    fun findByUserId(userId : String) : EMailAuth?

    fun findByeMail(eMail: String) : EMailAuth?
}