package com.tinuproject.tinu.infra.swagger.annotation

import io.swagger.v3.oas.models.examples.Example

class ExampleHolder(
    val holder: Example?,
    val name: String?,
    val code : Int
) {


}