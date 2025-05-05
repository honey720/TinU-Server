package com.tinuproject.tinu.domain.post.hashtag.entity

import com.tinuproject.tinu.global.entity.BaseEntity
import com.tinuproject.tinu.domain.post.hashtag.posthashtagmap.entity.PostHashTagMap
import jakarta.persistence.*

@Entity
class HashTag (

    @Column
    var tagName : String,

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "hashTag")
    var postHashTagMap:  MutableList<PostHashTagMap> = mutableListOf()
) : BaseEntity()