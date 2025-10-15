package com.tinuproject.tinu.domain.member.entity

import com.tinuproject.tinu.domain.member.enums.Evaluation
import com.tinuproject.tinu.domain.post.entity.Post
import com.tinuproject.tinu.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
class Review(
    // 평가자
    // 평가자가 삭제되어도 피평가자에 대한 기록은 유지되어야함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = true)
    val reviewer: Member?,

    // 피평가자
    // 피평가자가 사라지면 평가를 남겨둘 이유가 없음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id", nullable = false)
    val reviewee : Member,

    // 평가 대상 Post
    // ManyToOne 인 이유 - 하나의 Post에서 2개의 리뷰가 나올 수 있어서.
    // 게시글이 삭제되어도 평가에 대한 기록은 남아 있어야함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = true)
    val post: Post?,

    // 메인 평가 점수 (좋았어요 보통이에요 나빴어요)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var mainEvaluation: Evaluation,

    // 보조 평가 플래그 3개
    @Column(nullable = false)
    var isFriendly: Boolean = false,

    @Column(nullable = false)
    var notLate: Boolean = false,

    @Column(nullable = false)
    var respondedQuickly: Boolean = false
) : BaseEntity() {
}

