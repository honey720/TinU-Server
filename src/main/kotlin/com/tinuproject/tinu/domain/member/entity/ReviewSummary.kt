package com.tinuproject.tinu.domain.member.entity

import jakarta.persistence.*

@Entity
class ReviewSummary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    @Column
    var mark : Double = 0.0,

    @Column
    var reviewCount : Int = 0,

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

    fun updateMainEvaluation(reviewScore : Double){
        val reviewNum = this.reviewCount

        val resultMark = ((this.mark * reviewNum) + reviewScore) / (reviewNum + 1)

        this.mark = resultMark

        this.reviewCount++
    }

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