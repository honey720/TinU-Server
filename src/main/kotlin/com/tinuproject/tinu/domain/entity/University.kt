package com.tinuproject.tinu.domain.entity

import com.tinuproject.tinu.domain.entity.base.BaseEntity
import jakarta.persistence.*


@Entity
class University (

    @OneToMany(mappedBy = "university")
    var members : MutableList<Member> ?= mutableListOf(),

    @OneToMany(mappedBy = "university")
    var domain : MutableList<UniversityDomain>?= mutableListOf(),

    @Column
    var name : String,

    ) : BaseEntity()