package com.tinuproject.tinu.domain.member.socialmember.entity

import com.tinuproject.tinu.domain.common.entity.BaseEntity
import com.tinuproject.tinu.domain.member.socialmember.enums.Social
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.util.UUID


@Entity
class SocialMember(
    @Column(columnDefinition = "BINARY(16)", unique = true)
    var userId : UUID,

    @Column
    var providerId : String,

    @Column
    @Enumerated(EnumType.ORDINAL)
    var provider : Social,

    ): BaseEntity()