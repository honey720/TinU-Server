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
        if(flag){
            this.respondedQuicklyNum++;
        }
        else{
            this.respondedQuicklyNum--
        }
    }

    fun updateNotLateNum(flag : Boolean){
        if(flag){
            this.respondedQuicklyNum++;
        }
        else{
            this.respondedQuicklyNum--
        }
    }

    fun updateRespondedQuicklyNum(flag : Boolean){
        if(flag){
            this.respondedQuicklyNum++;
        }
        else{
            this.respondedQuicklyNum--
        }
    }
}