package com.tinuproject.tinu.domain.member.entity

import jakarta.persistence.*

@Entity
class SubReviewSummary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    // 보조 평가 요약
    @Column(nullable = false)
    var isFriendlyNum: Int = 0,

    @Column(nullable = false)
    var notLateNum: Int = 0,

    @Column(nullable = false)
    var respondedQuicklyNum: Int = 0
) {


}