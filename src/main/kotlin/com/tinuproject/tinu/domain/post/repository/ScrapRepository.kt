package com.tinuproject.tinu.domain.post.repository

import com.tinuproject.tinu.domain.post.entity.Scrap
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ScrapRepository: JpaRepository<Scrap, Long> {
    @Query("SELECT s.post.id FROM Scrap s WHERE s.member.id = :memberId")
    fun findPostIdsByMemberId(@Param("memberId") memberId: Long): Set<Long>

    fun findScrapByMemberIdAndPostId(userId: Long, postId: Long): Scrap?
}