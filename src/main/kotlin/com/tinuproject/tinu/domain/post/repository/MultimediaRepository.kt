package com.tinuproject.tinu.domain.post.repository

import com.tinuproject.tinu.domain.entity.Multimedia
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface MultimediaRepository: CrudRepository<Multimedia, Long> {

    @Query("DELETE FROM Multimedia m WHERE m.post.id = :postId")
    @Modifying
    fun deleteAllByPostId(@Param("postId") postId: Long)
}