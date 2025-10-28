package com.tinuproject.tinu.domain.member.repository

import com.tinuproject.tinu.domain.member.entity.ReviewSummary
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ReviewSummaryRepository : JpaRepository<ReviewSummary,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM ReviewSummary r WHERE r.member.userId = :userId")
    fun findByMemberUserIdForUpdate(@Param("userId") userId: UUID): ReviewSummary?
}