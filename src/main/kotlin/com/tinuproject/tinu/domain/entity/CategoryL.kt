package com.tinuproject.tinu.domain.entity

import jakarta.persistence.*

@Entity
class CategoryL(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long ?= null,


    @Column
    var name : String,


    //category가 삭제된다고 필터링 cascade로 삭제 X 별도의 로직 필요
    @OneToMany(mappedBy = "categoryl",  fetch = FetchType.LAZY)
    var customCategoryL: MutableList<CustomCategoryL>


    )