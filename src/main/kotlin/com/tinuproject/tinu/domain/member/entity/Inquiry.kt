package com.tinuproject.tinu.domain.member.entity

import com.tinuproject.tinu.global.entity.BaseEntity
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
