package com.tinuproject.tinu.tempdomain.post.chat.entity

import com.tinuproject.tinu.tempdomain.post.chat.chattext.entity.ChatText
import com.tinuproject.tinu.tempdomain.post.entity.Post
import com.tinuproject.tinu.tempdomain.common.entity.BaseEntity
import com.tinuproject.tinu.tempdomain.member.entity.Member
import jakarta.persistence.*

@Entity
class Chat (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="buyer_id")
    var buyer : Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller_id")
    var seller : Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    var post : Post,

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "chat")
    var chatList : MutableList<ChatText> = mutableListOf()
) : BaseEntity()
