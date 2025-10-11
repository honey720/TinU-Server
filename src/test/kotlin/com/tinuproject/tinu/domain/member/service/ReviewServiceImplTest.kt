package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.annotation.ServiceTest
import com.tinuproject.tinu.domain.member.enums.Evaluation
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.member.repository.ReviewRepository
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import com.tinuproject.tinu.domain.post.repository.CategoryRepository
import com.tinuproject.tinu.domain.post.repository.PostRepository
import com.tinuproject.tinu.domain.university.repository.UniversityRepository
import com.tinuproject.tinu.factory.TestCategoryFactory
import com.tinuproject.tinu.factory.TestMemberFactory
import com.tinuproject.tinu.factory.TestPostFactory
import com.tinuproject.tinu.factory.TestUniversityFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import kotlin.test.Test


import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple


@ServiceTest
class ReviewServiceImplTest(
) {

    @Autowired
    private lateinit var reviewService: ReviewService

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var universityRepository: UniversityRepository

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var reviewRepository: ReviewRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository


    @AfterEach
    fun tearDown() {
        memberRepository.deleteAllInBatch()
        postRepository.deleteAllInBatch()
        reviewRepository.deleteAllInBatch()
        universityRepository.deleteAllInBatch()
        categoryRepository.deleteAll()
    }


    @Test
    @DisplayName("")
    fun hasWrittenReviewTest(){
        //Given

        //When

        //Then
    }

    @Test
    @DisplayName("거래 리뷰를 남기려할때 Reviewer Reviewee Post에 대한 정보가 모두 맞을 때 결과 true 반환.")
    fun createReviewTest(){
        //Given
        val testUniversity = universityRepository.save(TestUniversityFactory.create())

        //유저 정보 기입
        val reviewerId = UUID.randomUUID()
        val revieweeId = UUID.randomUUID()
        val reviewer = TestMemberFactory.create(
            memberRepository = memberRepository,
            university = testUniversity,
            nickname = "리뷰어",
            userId = reviewerId
        )

        val reviewee = TestMemberFactory.create(
            memberRepository = memberRepository,
            university = testUniversity,
            nickname = "피리뷰어",
            userId = revieweeId
        )

        //게시글 정보 기입
        val testCategory = TestCategoryFactory.create(categoryRepository = categoryRepository)

        val post = TestPostFactory.create(
            postRepository = postRepository,
            author = reviewee,
            buyer = reviewer,
            university = testUniversity,
            category = testCategory
        )
        //When
        val result  = reviewService.createReview(
            createReviewInput = CreateReviewInput(
                reviewerId = reviewerId,
                revieweeId =  revieweeId,
                postId = post.id!!,
                mainEvaluation = Evaluation.GOOD,
                isFriendly = true,
                notLate = false,
                respondedQuickly = true
            )
        )

        //Then

        assertThat(result).isTrue()

        val resultReviewer = memberRepository.findMemberByUserId(revieweeId)

        assertThat(resultReviewer!!.subEvaluationSummary)
            .extracting("isFriendlyNum","notLateNum","respondedQuicklyNum")
            .containsExactly(1, -1, 1)
    }

}