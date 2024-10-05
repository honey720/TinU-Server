package com.tinuproject.tinu.domain.entity

import com.tinuproject.tinu.domain.enum.PaymentMethod
import com.tinuproject.tinu.domain.enum.ReportCategory
import jakarta.persistence.*

@Entity
class Report (


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long ?= null,

    //단방향 조회로 구성하기에 Member에 따로 추가 X
    //유저 삭제시 같이 삭제되는 로직 필요
    @ManyToOne
    @JoinColumn(name = "member_id")
    var reporter : Member,


    //단방향 조회로 구성하기에 Member에 따로 추가 X
    //유저 삭제시 같이 삭제되는 로직 필요
    @ManyToOne
    @JoinColumn(name = "member_id")
    var respondent : Member,

    //단방향 조회로 구성하여 Post에 따로 추가 X
    //유저 삭제시 같이 삭제되는 로직 필요
    @ManyToOne
    @JoinColumn(name = "post_id")
    var post : Post,


    @ElementCollection(targetClass = PaymentMethod::class)
    @Enumerated(EnumType.STRING)
    @Column
    @CollectionTable(name = "report_category", joinColumns = [JoinColumn(name = "post_id")])
    var reportCategory : Set<ReportCategory> = setOf(),


    @Column(nullable = true,columnDefinition = "TEXT")
    var body : String

)