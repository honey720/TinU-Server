package com.tinuproject.tinu.domain.entity

import jakarta.persistence.*

@Entity
class Chat (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long ?= null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var buyer : Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var seller : Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    var post : Post,

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "member")
    var chatList : MutableList<ChatText> = mutableListOf()
)
