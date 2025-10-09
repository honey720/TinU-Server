package com.tinuproject.tinu.factory

import com.tinuproject.tinu.domain.university.entity.University
import com.tinuproject.tinu.domain.university.repository.UniversityRepository

object TestUniversityFactory {
    fun create() : University{
        return University(name="경기대")
    }
}