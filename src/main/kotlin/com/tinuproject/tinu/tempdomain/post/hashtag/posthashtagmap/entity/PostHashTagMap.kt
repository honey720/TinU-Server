package com.tinuproject.tinu.tempdomain.post.hashtag.posthashtagmap.entity

import com.tinuproject.tinu.tempdomain.post.hashtag.entity.HashTag
import com.tinuproject.tinu.tempdomain.common.entity.BaseEntity
import com.tinuproject.tinu.tempdomain.post.entity.Post
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