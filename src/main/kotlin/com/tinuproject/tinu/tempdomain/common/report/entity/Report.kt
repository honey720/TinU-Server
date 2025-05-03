package com.tinuproject.tinu.tempdomain.common.report.entity

import com.tinuproject.tinu.tempdomain.post.entity.Post
import com.tinuproject.tinu.tempdomain.common.entity.BaseEntity
import com.tinuproject.tinu.tempdomain.common.report.enums.ReportCategory
import com.tinuproject.tinu.tempdomain.member.entity.Member
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