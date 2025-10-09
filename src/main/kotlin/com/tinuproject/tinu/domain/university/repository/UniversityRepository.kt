package com.tinuproject.tinu.domain.university.repository

import com.tinuproject.tinu.domain.university.entity.University
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UniversityRepository : JpaRepository<University, Long>{
}