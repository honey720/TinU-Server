package com.tinuproject.tinu.annotation

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.annotation.AliasFor
import org.springframework.test.context.ActiveProfiles
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@WebMvcTest
@ActiveProfiles("test")
annotation class  MockControllerTest (
    @get:AliasFor(annotation = WebMvcTest::class, attribute = "controllers")
    val controllers: Array<KClass<*>> = []
)