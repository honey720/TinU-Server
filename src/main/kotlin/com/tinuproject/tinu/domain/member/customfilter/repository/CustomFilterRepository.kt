package com.tinuproject.tinu.domain.member.customfilter.repository

import com.tinuproject.tinu.domain.member.customfilter.entity.CustomFilter
import org.springframework.data.jpa.repository.JpaRepository

interface CustomFilterRepository : JpaRepository<CustomFilter, Long> {
    fun findCustomFilterById(id : Long) : CustomFilter?
}