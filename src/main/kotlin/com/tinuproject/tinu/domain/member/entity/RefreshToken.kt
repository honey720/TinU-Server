package com.tinuproject.tinu.domain.member.entity

import jakarta.persistence.*
import java.util.*

@Entity
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?= null,

    @Column(unique = true)
    var userId : UUID,

    @Column(unique = true)
    var token : String
){
}