package com.tinuproject.tinu.domain.member.entity

import jakarta.persistence.*

@Entity
class SubEvaluationSummary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    // 보조 평가 요약
    @Column(nullable = false)
    var isFriendlyNum: Int = 0,

    @Column(nullable = false)
    var notLateNum: Int = 0,

    @Column(nullable = false)
    var respondedQuicklyNum: Int = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member

) {


    fun updateFriendlyNum(flag : Boolean){
        this.isFriendlyNum += if(flag) 1 else -1
    }

    fun updateNotLateNum(flag : Boolean){
        this.notLateNum += if(flag) 1 else -1
    }

    fun updateRespondedQuicklyNum(flag : Boolean){
        this.respondedQuicklyNum += if(flag) 1 else -1
    }
}