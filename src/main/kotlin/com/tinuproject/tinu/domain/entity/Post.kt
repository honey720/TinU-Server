package com.tinuproject.tinu.domain.entity

import com.tinuproject.tinu.domain.enum.PaymentMethod
import com.tinuproject.tinu.domain.enum.SellMethod
import jakarta.persistence.*

@Entity
class Post (
    //id, 학교(외래), 제목, 내용, 작성시간(base),
    // 카테고리(외래) 논의점,
    // 가격, 판매방식, 숨김상태,
    // 거래방식, 썸네일(반정규화), 신고누적(반정규화)
    @Id
    @GeneratedValue
    var id : Long ?= null,

    @ManyToOne
    var university : University,

    @Column
    var title : String,

    @Column(columnDefinition = "TEXT")
    var body : String,

    @OneToMany(mappedBy = "post")
    var categoryL : MutableList<CategoryL>,

    @OneToMany(mappedBy = "post")
    var CategoryM : MutableList<CategoryM>,

    @Column
    var price : Int,

    @ElementCollection(targetClass = SellMethod::class)
    @CollectionTable(name = "sell_method", joinColumns = [JoinColumn(name = "post_id")])
    @Enumerated(EnumType.STRING)
    @Column
    var sellMethod : Set<SellMethod> = setOf(),

    @Column
    var hide : Boolean,

    @ElementCollection(targetClass = PaymentMethod::class)
    @CollectionTable(name = "payment_method", joinColumns = [JoinColumn(name = "post_id")])
    @Enumerated(EnumType.STRING)
    @Column
    var paymentMethod: Set<PaymentMethod> = setOf(),

    @Column
    var thumbnailImageUrl : String,

    @Column
    var reports : Long,

    @OneToMany(mappedBy = "mutimedia")
    var multimedia : MutableList<Multimedia>
)
