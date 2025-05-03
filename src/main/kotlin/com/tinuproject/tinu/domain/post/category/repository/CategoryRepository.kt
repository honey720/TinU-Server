package com.tinuproject.tinu.domain.post.category.repository

import com.tinuproject.tinu.domain.post.category.entity.Category
import org.springframework.data.repository.CrudRepository

interface CategoryRepository : CrudRepository<Category, Long>{
    fun findCategoryById(categoryId : Long) : Category?
}