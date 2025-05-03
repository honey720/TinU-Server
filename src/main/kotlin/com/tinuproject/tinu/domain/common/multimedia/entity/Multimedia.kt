package com.tinuproject.tinu.domain.common.multimedia.entity

import com.tinuproject.tinu.domain.post.entity.Post
import com.tinuproject.tinu.domain.common.entity.BaseEntity
import jakarta.persistence.*


@Entity
class Multimedia (

    @Column
    var url : String,

    @Column
    var isImage : Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    var post : Post
) : BaseEntity()