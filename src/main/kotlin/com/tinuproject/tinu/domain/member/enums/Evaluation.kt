package com.tinuproject.tinu.domain.member.enums

enum class Evaluation(
    val score : Double,
    val text : String

){
    GOOD(4.5,"만족해요."),
    SOSO( 2.5,"평범해요."),
    BAD(1.0,"아쉬워요.")
}