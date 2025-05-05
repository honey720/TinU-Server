package com.tinuproject.tinu.domain.post.scrap.entity

import com.tinuproject.tinu.global.entity.BaseEntity
import com.tinuproject.tinu.tempdomain.member.entity.Member
import com.tinuproject.tinu.domain.post.entity.Post
import jakarta.persistence.*

@Entity
class Scrap (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var member : Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post : Post
) : BaseEntity()