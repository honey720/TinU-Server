package com.tinuproject.tinu.domain.customcategory.repository

import com.tinuproject.tinu.domain.entity.CustomCategory
import org.springframework.data.jpa.repository.JpaRepository

interface CustomCategoryRepository : JpaRepository<CustomCategory,Long>  {
}