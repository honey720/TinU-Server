package com.tinuproject.tinu.infra.emailauth.util

import jakarta.mail.internet.MimeMessage

interface MailSender {
    fun createNumber()

    fun createEmail(mail: String?) : MimeMessage

    fun sendMail(mail: String?) : String
}