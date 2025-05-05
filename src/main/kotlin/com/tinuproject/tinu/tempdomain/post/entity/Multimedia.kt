package com.tinuproject.tinu.tempdomain.post.entity

import com.tinuproject.tinu.domain.post.entity.Post
import com.tinuproject.tinu.global.entity.BaseEntity
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