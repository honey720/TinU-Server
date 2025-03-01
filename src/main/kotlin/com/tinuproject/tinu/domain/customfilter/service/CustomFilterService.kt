package com.tinuproject.tinu.domain.customfilter.service

import com.tinuproject.tinu.domain.customfilter.dto.client_controller.request.UpdateCustomFilter
import com.tinuproject.tinu.domain.customfilter.dto.client_controller.response.SelectCustomFilter
import java.util.*

interface CustomFilterService {
    fun getUserCustomFilter(userId : UUID) : List<SelectCustomFilter>

    fun updateUserCustomFilter(userId : UUID, updateCustomFilter: UpdateCustomFilter)
}