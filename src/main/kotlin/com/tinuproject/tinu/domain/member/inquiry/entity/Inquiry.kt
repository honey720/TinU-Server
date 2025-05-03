package com.tinuproject.tinu.domain.member.inquiry.entity

import com.tinuproject.tinu.domain.common.entity.BaseEntity
import com.tinuproject.tinu.domain.member.entity.Member
import jakarta.persistence.*

//문의 Entity
@Entity
class Inquiry (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var inquirer : Member,

    @Column
    var title : String,

    @Column
    var body : String,

    @Column
    var answer : String?,

    @Column
    var isAnswer : Boolean
) : BaseEntity()
