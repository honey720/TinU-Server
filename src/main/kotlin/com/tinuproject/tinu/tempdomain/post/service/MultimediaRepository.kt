package com.tinuproject.tinu.tempdomain.post.service

import com.tinuproject.tinu.tempdomain.post.entity.Multimedia
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MultimediaRepository: JpaRepository<Multimedia, Long> {

    @Query("DELETE FROM Multimedia m WHERE m.post.id = :postId")
    @Modifying
    fun deleteAllByPostId(@Param("postId") postId: Long)
}