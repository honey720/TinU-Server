package com.tinuproject.tinu.swagger.annotation

import com.tinuproject.tinu.domain.exception.base.ErrorCode

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SwaggerErrorResponsesByEnum(
    val errorCodes: Array<ErrorCode> = []
){

}
