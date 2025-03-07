package com.tinuproject.tinu.domain.post.repository

import com.tinuproject.tinu.domain.entity.PostHashTagMap
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface PostHastTagMapRepository: CrudRepository<PostHashTagMap, Long> {
    fun findAllByPostId(postId: Long): List<PostHashTagMap>

    @Query("DELETE FROM PostHashTagMap p WHERE p.post.id = :postId")
    @Modifying
    fun deleteAllByPostId(@Param("postId") postId: Long)
}