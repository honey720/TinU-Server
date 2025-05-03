package com.tinuproject.tinu.tempdomain.post.category.repository

import com.tinuproject.tinu.tempdomain.post.category.entity.Category
import org.springframework.data.repository.CrudRepository

interface CategoryRepository : CrudRepository<Category, Long>{
    fun findCategoryById(categoryId : Long) : Category?
}