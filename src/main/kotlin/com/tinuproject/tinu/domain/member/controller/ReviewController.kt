package com.tinuproject.tinu.domain.member.controller

import com.tinuproject.tinu.domain.member.controller.dto.request.CreateReviewRequest
import com.tinuproject.tinu.domain.member.controller.dto.request.SearchWriteReviewRequest
import com.tinuproject.tinu.domain.member.exception.ExistReviewException
import com.tinuproject.tinu.domain.member.service.ReviewService
import com.tinuproject.tinu.domain.post.exception.PostNotFoundException
import com.tinuproject.tinu.global.exception.UnauthorizedAccessException
import com.tinuproject.tinu.global.response.ResponseEntityGenerator
import com.tinuproject.tinu.global.response.dto.ResponseDTO
import com.tinuproject.tinu.infra.swagger.annotation.SwaggerExceptionResponses
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("api/reviews")
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
    @Operation(summary = "거래 리뷰 작성 API", description = "거래 리뷰 작성 API입니다." +
            "<br>필수 파라미터 : postId, mainEvaluation, subEvaluation" +
            "<br>mainEvaluation : 평가 페이지 최상단 거래에 대한 총 평가입니다.(좋았어요 GOOD, 보통이에요 SOSO, 별로였어요 BAD 로 보내주시면 됩니다." +
            "<br>subEvaluation :  평가 페이지 하단 3가지 보조 평가입니다. 대화가 친절했어요, 시간을 준수했어요, 응답이 빨랐어요 순서로 리스트에 담아 true false로 보내주시면 됩니다.")
    fun createReview(@AuthenticationPrincipal userId : UUID, @RequestBody request: CreateReviewRequest) : ResponseEntity<ResponseDTO<Boolean?>> {
        return ResponseEntityGenerator.onSuccess(reviewService.createReview(request.of(userId)))
    }

    @GetMapping("")
    @SwaggerExceptionResponses(
        exceptions = [
            PostNotFoundException::class
        ]
    )
    @Operation(summary = "거래 리뷰 필요 여부 확인 API", description = "거래 리뷰 필요 여부  API입니다." +
            "<br> 거래 완료 게시글과 연관된 채팅방에 들어갈 경우 해당 사용자가 리뷰를 작성해야하는 지 여부를 확인하는 과정입니다." +
            "<br>필수 파라미터 : postId" +
            "<br> 해당 결과 값이 true 일 경우 리뷰 작성 필요, false 일 경우 리뷰 작성 불필요입니다."
    )
    fun checkAlreadyWriteReview(@AuthenticationPrincipal userId : UUID, @RequestBody request: SearchWriteReviewRequest):ResponseEntity<ResponseDTO<Boolean?>>{
        return ResponseEntityGenerator.onSuccess(reviewService.needWrittenReview(request.of(userId)))
    }
}