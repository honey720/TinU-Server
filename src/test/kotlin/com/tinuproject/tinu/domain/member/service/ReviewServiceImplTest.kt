package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.annotation.ServiceTest
import com.tinuproject.tinu.domain.member.entity.Member
import com.tinuproject.tinu.domain.member.enums.Evaluation
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.member.repository.ReviewRepository
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import com.tinuproject.tinu.domain.post.entity.Post
import com.tinuproject.tinu.domain.post.repository.CategoryRepository
import com.tinuproject.tinu.domain.post.repository.PostRepository
import com.tinuproject.tinu.domain.university.entity.University
import com.tinuproject.tinu.domain.university.repository.UniversityRepository
import com.tinuproject.tinu.factory.TestCategoryFactory
import com.tinuproject.tinu.factory.TestMemberFactory
import com.tinuproject.tinu.factory.TestPostFactory
import com.tinuproject.tinu.factory.TestUniversityFactory
import com.tinuproject.tinu.global.exception.UnauthorizedAccessException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import kotlin.test.Test


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import software.amazon.awssdk.services.s3.endpoints.internal.Eval

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

    private fun createMember(university: University) : Member {

        return TestMemberFactory.create(
            memberRepository = memberRepository,
            university = university,
            nickname = "테스트",
            userId = UUID.randomUUID()
        )
    }


    @Test
    @DisplayName("거래 리뷰를 남기려할때 Reviewer Reviewee Post에 대한 정보가 모두 맞을 때 결과 true 반환.")
    fun createReviewTest(){
        //Given
        val testUniversity = universityRepository.save(TestUniversityFactory.create())

        //유저 정보 기입
        val reviewer = createMember(testUniversity)

        val reviewee = createMember(testUniversity)

        val reviewerId = reviewer.userId

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
                postId = post.id!!,
                mainEvaluation = Evaluation.GOOD,
                isFriendly = true,
                notLate = false,
                respondedQuickly = true
            )
        )

        //Then
        assertThat(result).isTrue()

        val resultReviewer = memberRepository.findMemberByUserId(reviewee.userId)

        assertThat(resultReviewer!!.subEvaluationSummary)
            .extracting("isFriendlyNum","notLateNum","respondedQuicklyNum")
            .containsExactly(1, -1, 1)
    }

    @Test
    @DisplayName("리뷰 작성 중 Post가 존재하지 않거나, 구매 설정이 완료되지 않거나, 작성자가 해당 게시글 실 거래자가 아닌 경우 UnAuthorization Exception 반환")
    fun createReviewUnAutoriztionExceptionTest(){
        //Given
        val testUniversity = universityRepository.save(TestUniversityFactory.create())

        //유저 정보 기입
        val reviewer = createMember(testUniversity)

        val reviewee = createMember(testUniversity)

        val badUser = createMember(testUniversity)

        val reviewerId = reviewer.userId

        //게시글 정보 기입
        val testCategory = TestCategoryFactory.create(categoryRepository = categoryRepository)

        val post = TestPostFactory.create(
            postRepository = postRepository,
            author = reviewee,
            buyer = reviewer,
            university = testUniversity,
            category = testCategory
        )

        //when
        assertThrows<UnauthorizedAccessException>{
            reviewService.createReview(createReviewInput = CreateReviewInput(
                reviewerId = badUser.userId,
                postId = post.id!!,
                mainEvaluation = Evaluation.GOOD,
                isFriendly = true,
                notLate = false,
                respondedQuickly = true
            ))
        }


        //then

    }

}