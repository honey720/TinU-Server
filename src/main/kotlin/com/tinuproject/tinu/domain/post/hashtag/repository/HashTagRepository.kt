package com.tinuproject.tinu.domain.post.hashtag.repository

import com.tinuproject.tinu.domain.post.hashtag.entity.HashTag
import org.springframework.data.repository.CrudRepository

interface HashTagRepository: CrudRepository<HashTag, Long> {
    fun findAllByTagNameIn(tagNames : List<String>) : List<HashTag>
}