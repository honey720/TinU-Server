package com.tinuproject.tinu.swagger.annotation

import com.tinuproject.tinu.domain.exception.base.BaseException
import com.tinuproject.tinu.domain.exception.base.ErrorCode
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SwaggerErrorResponseByClass(
    val errorCodes: Array<KClass<out BaseException>> = []
){

}
