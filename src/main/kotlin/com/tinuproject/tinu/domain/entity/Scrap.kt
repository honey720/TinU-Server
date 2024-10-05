package com.tinuproject.tinu.domain.entity

import jakarta.persistence.*

@Entity
class Scrap (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long ?= null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var member : Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post : Post
)