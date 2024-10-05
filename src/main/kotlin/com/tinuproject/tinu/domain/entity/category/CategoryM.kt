package com.tinuproject.tinu.domain.entity.category

import com.tinuproject.tinu.domain.entity.Post
import jakarta.persistence.*

@Entity
class CategoryM (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long ?= null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryL_id")
    var categoryL: CategoryL,

    @Column
    var name : String,

    //category가 삭제된다고 필터링 cascade로 삭제 X 별도의 로직 필요
    @OneToMany(mappedBy = "categorym",  fetch = FetchType.LAZY)
    var customCategoryM: MutableList<CustomCategoryM>,


    @OneToMany(mappedBy = "categorym", fetch = FetchType.LAZY)
    var posts : MutableList<Post>
)