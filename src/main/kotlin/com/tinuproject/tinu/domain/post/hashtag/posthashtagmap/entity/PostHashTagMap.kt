package com.tinuproject.tinu.domain.post.hashtag.posthashtagmap.entity

import com.tinuproject.tinu.domain.post.hashtag.entity.HashTag
import com.tinuproject.tinu.domain.common.entity.BaseEntity
import com.tinuproject.tinu.domain.post.entity.Post
import jakarta.persistence.*

@Entity
class PostHashTagMap (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post : Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashTag_id")
    var hashTag : HashTag
) : BaseEntity()