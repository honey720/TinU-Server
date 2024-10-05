package com.tinuproject.tinu.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?= null;
}