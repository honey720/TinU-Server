package com.tinuproject.tinu.domain.post.entity

import com.tinuproject.tinu.global.entity.BaseEntity
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