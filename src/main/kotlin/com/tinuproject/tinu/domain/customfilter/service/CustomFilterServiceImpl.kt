package com.tinuproject.tinu.domain.customfilter.service

import com.tinuproject.tinu.domain.customfilter.dto.client_controller.request.UpdateCustomFilter
import com.tinuproject.tinu.domain.customfilter.dto.client_controller.response.SelectCustomFilter
import com.tinuproject.tinu.domain.customfilter.repository.CustomFilterRepository
import com.tinuproject.tinu.domain.exception.mail.NotExistMemberException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import java.util.*

class CustomFilterServiceImpl(
    val memberRepository: MemberRepository,
    val customFilterRepository: CustomFilterRepository
):CustomFilterService {
    override fun getUserCustomFilter(userId: UUID): List<SelectCustomFilter> {
        val result = mutableListOf<SelectCustomFilter>()

        val member = memberRepository.findMemberByUserId(userId = userId)?:throw NotExistMemberException()

        val customFilters = member.customFilter

        for(customFilter in customFilters){
            val categorys = mutableListOf<Long>()

            for(category in customFilter.customCategory){
                categorys.add(category.id!!)
            }

            result.add(SelectCustomFilter(
                filterId = customFilter.id!!,
                filterName =  customFilter.filterName,
                maxPrice = customFilter.maxPrice,
                minPrice = customFilter.minPrice,
                isSell = customFilter.isSell,
                category = categorys
            ))
        }

        return result
    }

    override fun updateUserCustomFilter(userId: UUID, updateCustomFilter: UpdateCustomFilter) {
        TODO("Not yet implemented")
    }
}