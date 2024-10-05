package com.tinuproject.tinu.domain.entity.category

import com.tinuproject.tinu.domain.entity.CustomFilter
import jakarta.persistence.*


@Entity
class CustomCategoryM (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long ?= null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customfilter_id")
    var customFilters : CustomFilter,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorym_id")
    var categoryM : CategoryM



)