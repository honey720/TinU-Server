package com.tinuproject.tinu.domain.post.repository

import com.tinuproject.tinu.domain.entity.Category
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: CrudRepository<Category, Long> {
    fun findCategoryById(categoryId : Long) : Category?

}