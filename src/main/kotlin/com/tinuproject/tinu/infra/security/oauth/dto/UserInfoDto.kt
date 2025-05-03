package com.tinuproject.tinu.infra.security.oauth.dto

import java.util.*

class UserInfoDto (
    var uuid : UUID,

    var name : String,

    var providerId : String,

    var provider : String
    ) {
}