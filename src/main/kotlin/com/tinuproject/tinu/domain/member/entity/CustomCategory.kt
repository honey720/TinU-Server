package com.tinuproject.tinu.domain.member.entity

import com.tinuproject.tinu.global.entity.BaseEntity
import com.tinuproject.tinu.domain.post.entity.Category
import jakarta.persistence.*

@Entity
class CustomCategory(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customFilter_id")
    var customFilter: CustomFilter,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category
) : BaseEntity()
