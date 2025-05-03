package com.tinuproject.tinu.domain.scrap.repository

import com.tinuproject.tinu.tempdomain.post.scrap.entity.Scrap
import org.springframework.data.jpa.repository.JpaRepository

interface ScrapRepository: JpaRepository<Scrap, Long> {
    fun findScrapByMemberIdAndPostId(memberId: Long, postId: Long): Scrap?
}