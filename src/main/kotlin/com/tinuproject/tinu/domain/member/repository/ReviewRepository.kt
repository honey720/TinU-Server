package com.tinuproject.tinu.domain.member.repository
import com.tinuproject.tinu.domain.member.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

    fun existsByReviewer_UserIdAndPost_Id(reviewerId : UUID, postId : Long) : Boolean
}