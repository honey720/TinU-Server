package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.domain.member.controller.dto.request.CreateReviewRequest
import com.tinuproject.tinu.domain.member.controller.dto.request.SearchWriteReviewRequest
import com.tinuproject.tinu.domain.member.exception.ExistReviewException
import com.tinuproject.tinu.domain.member.service.ReviewService
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import com.tinuproject.tinu.domain.post.exception.PostNotFoundException
import com.tinuproject.tinu.global.exception.UnauthorizedAccessException
import com.tinuproject.tinu.global.response.ResponseEntityGenerator
import com.tinuproject.tinu.global.response.dto.ResponseDTO
import com.tinuproject.tinu.infra.swagger.annotation.SwaggerExceptionResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("api/review")
@Tag(name="리뷰(거래 이후 평가) API", description = "리뷰(거래 이후 평가) 관련 API입니다.")
class ReviewController(
    private val reviewService: ReviewService
) {

    @PostMapping("")
    @SwaggerExceptionResponses(
        exceptions = [
            UnauthorizedAccessException::class,
            PostNotFoundException::class,
            ExistReviewException::class,
        ]
    )
    fun createReview(@AuthenticationPrincipal userId : UUID, @RequestBody createReviewRequest: CreateReviewRequest) : ResponseEntity<ResponseDTO<Boolean?>> {
        return ResponseEntityGenerator.onSuccess(reviewService.createReview(createReviewRequest.of(userId)))
    }

    @GetMapping("")
    fun checkAlreadyWriteReview(@AuthenticationPrincipal userId : UUID, @RequestBody searchWriteReviewRequest: SearchWriteReviewRequest):ResponseEntity<ResponseDTO<Boolean?>>{
        return ResponseEntityGenerator.onSuccess(reviewService.hasWrittenReview(searchWriteReviewRequest.of(userId)))

    }
}