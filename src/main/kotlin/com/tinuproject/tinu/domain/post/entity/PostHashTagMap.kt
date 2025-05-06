package com.tinuproject.tinu.domain.post.entity

import com.tinuproject.tinu.global.entity.BaseEntity
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