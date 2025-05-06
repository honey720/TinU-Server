package com.tinuproject.tinu.domain.member.repository

import com.tinuproject.tinu.domain.member.entity.CustomFilter
import org.springframework.data.jpa.repository.JpaRepository

interface CustomFilterRepository : JpaRepository<CustomFilter, Long> {
    fun findCustomFilterById(id : Long) : CustomFilter?
}