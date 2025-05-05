package com.tinuproject.tinu.tempdomain.member.repository

import com.tinuproject.tinu.tempdomain.member.entity.CustomFilter
import org.springframework.data.jpa.repository.JpaRepository

interface CustomFilterRepository : JpaRepository<CustomFilter, Long> {
    fun findCustomFilterById(id : Long) : CustomFilter?
}