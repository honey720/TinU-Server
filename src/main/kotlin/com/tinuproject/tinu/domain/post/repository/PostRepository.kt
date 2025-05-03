package com.tinuproject.tinu.domain.post.repository

import com.tinuproject.tinu.tempdomain.post.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {
    fun findPostById(id: Long): Post?
}