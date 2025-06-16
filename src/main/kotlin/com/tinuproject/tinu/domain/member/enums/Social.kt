package com.tinuproject.tinu.domain.member.enums

enum class Social(var code:Int, var company : String) {
    KAKAO(0, "카카오"),
    GOOGLE(1, "구글"),
    NAVER(2, "네이버"),
    APPLE(4,"애플");


    companion object{
        fun getSocial(provider : String) : Social {
            val social : Social

            if(provider == "Kakao") social = KAKAO
            else if(provider == "Google") social = GOOGLE
            else if(provider == "Apple") social = APPLE
            else social = NAVER

            return social
        }
    }

}