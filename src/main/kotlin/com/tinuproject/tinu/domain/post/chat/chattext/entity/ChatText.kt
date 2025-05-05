package com.tinuproject.tinu.domain.post.chat.chattext.entity

import com.tinuproject.tinu.global.entity.BaseEntity
import com.tinuproject.tinu.domain.post.chat.enums.ChatType
import com.tinuproject.tinu.tempdomain.member.entity.Member
import com.tinuproject.tinu.domain.post.chat.entity.Chat
import jakarta.persistence.*

@Entity
class ChatText (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_id")
    var chat : Chat,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var writer : Member,

    @Column
    var text : String,

    @Column
    @Enumerated(EnumType.STRING)
    var type : ChatType,

    @Column
    var isRead : Boolean
) : BaseEntity()