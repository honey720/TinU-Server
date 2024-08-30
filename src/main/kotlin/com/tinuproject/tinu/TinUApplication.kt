package com.tinuproject.tinu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TinUApplication

fun main(args: Array<String>) {
	runApplication<TinUApplication>(*args)
}
