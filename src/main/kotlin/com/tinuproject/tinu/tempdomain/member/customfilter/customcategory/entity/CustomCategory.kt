package com.tinuproject.tinu.tempdomain.member.customfilter.customcategory.entity

import com.tinuproject.tinu.tempdomain.common.entity.BaseEntity
import com.tinuproject.tinu.tempdomain.member.customfilter.entity.CustomFilter
import com.tinuproject.tinu.tempdomain.post.category.entity.Category
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
