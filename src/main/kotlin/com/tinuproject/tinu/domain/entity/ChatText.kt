package com.tinuproject.tinu.domain.entity

import com.tinuproject.tinu.domain.enum.ChatRole
import com.tinuproject.tinu.domain.enum.ChatType
import jakarta.persistence.*

@Entity
class ChatText (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long ?= null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_id")
    var chat : Chat,

    //단방향 맵핑이어도 괜찮을 것으로 판단
    //유저는 자신이 작성한 ChatText를 기억하고 있을 필요가 없음.
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
)