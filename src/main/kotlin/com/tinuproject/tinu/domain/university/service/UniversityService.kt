package com.tinuproject.tinu.domain.university.service

import org.springframework.stereotype.Service

interface UniversityService {

    fun testUniversityAdd()
    fun existDomain(domain : String) : Boolean
}