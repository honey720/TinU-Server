package com.tinuproject.tinu.domain.post.enums

enum class PaymentMethod(var code : Int, var text : String) {

    ACCOUNTTRANSFER(0, "계좌이체"),
    CASH(1, "현금결제")
}