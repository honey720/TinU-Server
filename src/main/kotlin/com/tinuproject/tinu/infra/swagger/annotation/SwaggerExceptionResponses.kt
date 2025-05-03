package com.tinuproject.tinu.infra.swagger.annotation

import com.tinuproject.tinu.domain.common.exception.base.BaseException
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SwaggerExceptionResponses(
    val exceptions: Array<KClass<out BaseException>> = []
){

}
