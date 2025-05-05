package com.tinuproject.tinu.domain.post.repository

import com.tinuproject.tinu.domain.post.entity.Scrap
import org.springframework.data.jpa.repository.JpaRepository

interface ScrapRepository: JpaRepository<Scrap, Long> {
    fun findScrapByMemberIdAndPostId(memberId: Long, postId: Long): Scrap?
}