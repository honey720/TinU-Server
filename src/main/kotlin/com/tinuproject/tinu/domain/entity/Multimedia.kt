package com.tinuproject.tinu.domain.entity

import jakarta.persistence.*


@Entity
class Multimedia (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null,

    @Column
    var url : String,

    @Column
    var isImage : Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    var post : Post,
)