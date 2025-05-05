package com.tinuproject.tinu.tempdomain.member.entity


import com.tinuproject.tinu.tempdomain.member.controller.dto.request.UpdateCustomFilter
import com.tinuproject.tinu.domain.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
class CustomFilter (

    @Column
    var filterName : String,

    @Column
    var maxPrice : Int?,

    @Column
    var minPrice :Int?,

    @Column
    var onlySell : Boolean?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var member : Member,

    @OneToMany(mappedBy = "customFilter", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var customCategory: MutableList<CustomCategory> = mutableListOf()
) : BaseEntity(){

    fun updateCustomFilter(updateCustomFilter: UpdateCustomFilter){
        this.filterName = updateCustomFilter.filterName
        this.onlySell = updateCustomFilter.onlySell
        this.maxPrice = updateCustomFilter.maxPrice
        this.minPrice = updateCustomFilter.minPrice
    }
}