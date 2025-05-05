package com.tinuproject.tinu.domain.report.entity

import com.tinuproject.tinu.domain.post.entity.Post
import com.tinuproject.tinu.global.entity.BaseEntity
import com.tinuproject.tinu.domain.report.enums.ReportCategory
import com.tinuproject.tinu.domain.member.entity.Member
import jakarta.persistence.*

@Entity
class Report (

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    var reporter : Member,

    @ManyToOne
    @JoinColumn(name = "respondent_id")
    var respondent : Member,

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post : Post?,

    @Enumerated(EnumType.STRING)
    var reportCategory : ReportCategory,

    @Column(columnDefinition = "TEXT")
    var body : String?
) : BaseEntity()