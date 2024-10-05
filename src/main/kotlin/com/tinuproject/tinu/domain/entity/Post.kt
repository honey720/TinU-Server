package com.tinuproject.tinu.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Post {
    @Id
    @GeneratedValue
    var id : Long = 0
//    var id : Long ?= null
}
