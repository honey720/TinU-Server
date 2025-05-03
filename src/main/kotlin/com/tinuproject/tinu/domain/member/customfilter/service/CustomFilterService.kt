package com.tinuproject.tinu.domain.member.customfilter.service

import com.tinuproject.tinu.domain.member.customfilter.dto.request.CreateCustomFilter
import com.tinuproject.tinu.domain.member.customfilter.dto.request.DeleteCustomFilter
import com.tinuproject.tinu.domain.member.customfilter.dto.request.UpdateCustomFilter
import com.tinuproject.tinu.domain.member.customfilter.dto.response.SelectCustomFilter
import java.util.*

interface CustomFilterService {

    fun getCustomFilter(userId : UUID) : List<SelectCustomFilter>

    fun createCustomFilter(userId : UUID, createCustomFilter: CreateCustomFilter)

    fun updateCustomFilter(userId : UUID, updateCustomFilter: UpdateCustomFilter)

    fun deleteCustomFilter(userId : UUID, deleteCustomFilter: DeleteCustomFilter)
}