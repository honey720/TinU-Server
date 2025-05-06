package com.tinuproject.tinu.domain.post.repository

import com.tinuproject.tinu.domain.post.entity.Category
import org.springframework.data.repository.CrudRepository

interface CategoryRepository : CrudRepository<Category, Long>{
    fun findCategoryById(categoryId : Long) : Category?
}