package com.tinuproject.tinu.factory

import com.tinuproject.tinu.domain.university.entity.University
import com.tinuproject.tinu.domain.university.repository.UniversityRepository

object TestUniversityFactory {
    fun create(universityRepository: UniversityRepository) : University{
        return universityRepository.save(University(name="경기대"))
    }
}