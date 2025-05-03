package com.tinuproject.tinu.domain.member.customfilter.customcategory.entity

import com.tinuproject.tinu.domain.common.entity.BaseEntity
import com.tinuproject.tinu.domain.member.customfilter.entity.CustomFilter
import com.tinuproject.tinu.domain.post.category.entity.Category
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
