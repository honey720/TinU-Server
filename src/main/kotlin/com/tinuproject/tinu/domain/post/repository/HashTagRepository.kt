package com.tinuproject.tinu.domain.post.repository

import com.tinuproject.tinu.domain.entity.HashTag
import org.springframework.data.repository.CrudRepository

interface HashTagRepository: CrudRepository<HashTag, Long> {
    fun findHashTagByTagName(hashTag : String) : HashTag?
}