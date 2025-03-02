package com.tinuproject.tinu.domain.customfilter.service

import com.tinuproject.tinu.domain.category.repository.CategoryRepository
import com.tinuproject.tinu.domain.customcategory.repository.CustomCategoryRepository
import com.tinuproject.tinu.domain.customfilter.dto.client_controller.request.CreateCustomFilter
import com.tinuproject.tinu.domain.customfilter.dto.client_controller.request.UpdateCustomFilter
import com.tinuproject.tinu.domain.customfilter.dto.client_controller.response.SelectCustomFilter
import com.tinuproject.tinu.domain.customfilter.repository.CustomFilterRepository
import com.tinuproject.tinu.domain.entity.CustomCategory
import com.tinuproject.tinu.domain.entity.CustomFilter
import com.tinuproject.tinu.domain.exception.common.NotFoundException
import com.tinuproject.tinu.domain.exception.common.UnauthorizedAccessException
import com.tinuproject.tinu.domain.exception.customfilter.NotExistCustomFilter
import com.tinuproject.tinu.domain.exception.mail.NotExistMemberException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CustomFilterServiceImpl(
    val memberRepository: MemberRepository,
    val customFilterRepository: CustomFilterRepository,
    val customCategoryRepository: CustomCategoryRepository,
    val categoryRepository: CategoryRepository
):CustomFilterService {


    @Transactional(readOnly = true)
    override fun getCustomFilter(userId: UUID): List<SelectCustomFilter> {
        val result = mutableListOf<SelectCustomFilter>()

        val member = memberRepository.findMemberByUserId(userId = userId)?:throw NotExistMemberException()

        val customFilters = member.customFilter

        for(customFilter in customFilters){
            val categorys = mutableListOf<Long>()

            for(category in customFilter.customCategory){
                categorys.add(category.category.id!!)
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

    @Transactional
    override fun createCustomFilter(userId: UUID, createCustomFilter: CreateCustomFilter) {
        val member = memberRepository.findMemberByUserId(userId)?:throw NotExistMemberException()

        val customFilter = CustomFilter(
            filterName = createCustomFilter.filterName,
            isSell = createCustomFilter.isSell,
            maxPrice = createCustomFilter.maxPrice,
            minPrice = createCustomFilter.minPrice,
            member = member
        )

        customFilterRepository.save(customFilter)

        for(i  in createCustomFilter.category){
            val category = categoryRepository.findById(i).get()

            val customCategory = CustomCategory(
                category =  category,
                customFilter = customFilter
            )
            customCategoryRepository.save(customCategory)
        }
    }

    @Transactional
    override fun updateCustomFilter(userId: UUID, updateCustomFilter: UpdateCustomFilter) {
        val customFilter = customFilterRepository.findCustomFilterById(updateCustomFilter.filterId!!) ?: throw NotExistCustomFilter()

        if(customFilter.member.userId!=userId){
            throw UnauthorizedAccessException()
        }

        customFilter.updateCustomFilter(updateCustomFilter)

        customCategoryRepository.deleteAllByCustomFilter(customFilter = customFilter)

        customCategoryRepository.flush()

        mappingCustomCategory(customFilter,updateCustomFilter.category)
    }


    private fun mappingCustomCategory(customFilter: CustomFilter, categorys : MutableList<Long>){
        val categories = categoryRepository.findAllById(categorys)

        val customCategories = categories.map { category ->
            CustomCategory(category = category, customFilter = customFilter)
        }

        customCategoryRepository.saveAll(customCategories)
    }
}