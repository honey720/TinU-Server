package com.tinuproject.tinu.domain.customfilter.dto.client_controller.request

import org.jetbrains.annotations.NotNull

data class UpdateCustomFilter(
    val filterId : Long?,
    val filterName : String,
    val category : MutableList<Long>,
    val maxPrice : Int,
    val minPrice : Int,
    val isSell : Boolean
)
