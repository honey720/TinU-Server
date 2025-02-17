package com.tinuproject.tinu.web.email.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID


@Entity
class EMailAuth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null,

    @Column(name = "userId")
    var userId : String,

    @Column(name = "eMail")
    var eMail : String,

    @Column(name = "code")
    var code : String

) {
}