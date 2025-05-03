package com.tinuproject.tinu.tempdomain.member.customfilter.repository

import com.tinuproject.tinu.tempdomain.member.customfilter.entity.CustomFilter
import org.springframework.data.jpa.repository.JpaRepository

interface CustomFilterRepository : JpaRepository<CustomFilter, Long> {
    fun findCustomFilterById(id : Long) : CustomFilter?
}