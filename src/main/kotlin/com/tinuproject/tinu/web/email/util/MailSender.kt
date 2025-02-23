package com.tinuproject.tinu.web.email.util

import jakarta.mail.internet.MimeMessage

interface MailSender {
    fun createNumber()

    fun creatEmail(mail: String?) : MimeMessage

    fun sendMail(mail: String?) : String
}