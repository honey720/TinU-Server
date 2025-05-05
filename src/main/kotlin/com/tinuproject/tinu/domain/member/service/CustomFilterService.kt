package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.controller.dto.request.CreateCustomFilter
import com.tinuproject.tinu.domain.member.controller.dto.request.DeleteCustomFilter
import com.tinuproject.tinu.domain.member.controller.dto.request.UpdateCustomFilter
import com.tinuproject.tinu.domain.member.controller.dto.response.SelectCustomFilter
import java.util.*

interface CustomFilterService {

    fun getCustomFilter(userId : UUID) : List<SelectCustomFilter>

    fun createCustomFilter(userId : UUID, createCustomFilter: CreateCustomFilter)

    fun updateCustomFilter(userId : UUID, updateCustomFilter: UpdateCustomFilter)

    fun deleteCustomFilter(userId : UUID, deleteCustomFilter: DeleteCustomFilter)
}