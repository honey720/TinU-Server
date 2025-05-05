package com.tinuproject.tinu.domain.common.university.entity

import com.tinuproject.tinu.tempdomain.member.entity.Member
import com.tinuproject.tinu.domain.common.university.universitydomain.entity.UniversityDomain
import com.tinuproject.tinu.domain.common.entity.BaseEntity
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