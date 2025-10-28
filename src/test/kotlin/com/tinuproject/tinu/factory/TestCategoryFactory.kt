package com.tinuproject.tinu.factory

import com.tinuproject.tinu.domain.post.entity.Category
import com.tinuproject.tinu.domain.post.repository.CategoryRepository

object TestCategoryFactory {
    fun create(categoryRepository: CategoryRepository): Category {
        return categoryRepository.save(
            Category(
                name = "테스트카테고리",
                level = 1,
                parent = null
            )
        )
    }
}