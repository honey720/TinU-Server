package com.tinuproject.tinu.tempdomain.common.university.universitydomain.entity

import com.tinuproject.tinu.tempdomain.common.university.entity.University
import jakarta.persistence.*

@Entity
class UniversityDomain(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null,

    @Column
    var domain : String,

    @ManyToOne(fetch = FetchType.LAZY)
    var university : University

){

}
