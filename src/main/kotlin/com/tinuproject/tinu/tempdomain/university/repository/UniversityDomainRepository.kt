package com.tinuproject.tinu.tempdomain.university.repository

import com.tinuproject.tinu.tempdomain.university.entity.UniversityDomain
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UniversityDomainRepository : CrudRepository<UniversityDomain,Long> {
    fun existsByDomain(domain : String) : Boolean

    fun findByDomain(domain : String) : UniversityDomain?
}