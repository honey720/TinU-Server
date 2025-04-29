package com.tinuproject.tinu.domain.entity

import jakarta.persistence.*

@Entity
class UniversityDomain(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null,

    @Column
    var domain:String,

    @ManyToOne(fetch = FetchType.LAZY)
    var university : University

){

}
