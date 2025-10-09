package com.tinuproject.tinu.domain.member.repository

import com.tinuproject.tinu.annotation.RepositoryTest
import com.tinuproject.tinu.domain.member.entity.Member
import com.tinuproject.tinu.domain.member.entity.Review
import com.tinuproject.tinu.domain.member.enums.Evaluation
import com.tinuproject.tinu.domain.member.enums.Social
import com.tinuproject.tinu.domain.post.entity.Category
import com.tinuproject.tinu.domain.post.entity.Post
import com.tinuproject.tinu.domain.post.enums.SellMethod
import com.tinuproject.tinu.domain.post.repository.CategoryRepository
import com.tinuproject.tinu.domain.post.repository.PostRepository
import com.tinuproject.tinu.domain.university.entity.University
import com.tinuproject.tinu.domain.university.repository.UniversityRepository
import com.tinuproject.tinu.factory.TestCategoryFactory
import com.tinuproject.tinu.factory.TestMemberFactory
import com.tinuproject.tinu.factory.TestPostFactory
import com.tinuproject.tinu.factory.TestUniversityFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.mock
import java.util.*
import kotlin.test.Test

import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
class ReviewRepositoryTest(
) {

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
    }



    @Test
    @DisplayName("")
    fun tddLiveTemple(){
        //given

        //when

        //then

    }

    @Test
    @DisplayName("특정 유저가 특정 Post에서 평가를 진행했는지 확인할 때 이미 있다면 True를 리턴한다.")
    fun existsByReviewer_UserIdOrReviewee_UserIdAndPost_IdTest(){
        //given
        //대학 정보 기입
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

        val review = Review(
            reviewer = reviewer,
            reviewee = reviewer,
            post = post,
            mainEvaluation = Evaluation.GOOD
        )

        reviewRepository.save(review)

        //when & then
        assertThat(reviewRepository.existsByReviewer_UserIdOrReviewee_UserIdAndPost_Id(reviewerId, reviewerId, post.id!!)).isTrue()

    }
}