package com.tinuproject.tinu.annotation

import com.tinuproject.tinu.infra.swagger.adaptor.SwaggerExceptionResponseAdaptor
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor
import org.springframework.test.context.ActiveProfiles
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@WebMvcTest
@ActiveProfiles("test")
@Import(SwaggerExceptionResponseAdaptor::class)
annotation class  MockControllerTest (
    @get:AliasFor(annotation = WebMvcTest::class, attribute = "controllers")
    val controllers: Array<KClass<*>> = []
)