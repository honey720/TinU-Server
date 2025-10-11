package com.tinuproject.tinu.domain.member.repository

import com.tinuproject.tinu.domain.member.entity.SubEvaluationSummary
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface SubEvaluationSummaryRepository : JpaRepository<SubEvaluationSummary,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SubEvaluationSummary s WHERE s.member.userId = :userId")
    fun findByMemberUserIdForUpdate(@Param("userId") userId: UUID): SubEvaluationSummary?
}