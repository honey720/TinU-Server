package com.tinuproject.tinu.tempdomain.common.multimedia.entity

import com.tinuproject.tinu.tempdomain.post.entity.Post
import com.tinuproject.tinu.tempdomain.common.entity.BaseEntity
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