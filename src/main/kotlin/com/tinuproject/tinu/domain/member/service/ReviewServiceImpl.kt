package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.entity.Member
import com.tinuproject.tinu.domain.member.entity.Review
import com.tinuproject.tinu.domain.member.entity.SubEvaluationSummary
import com.tinuproject.tinu.domain.member.exception.ExistReviewException
import com.tinuproject.tinu.domain.member.repository.ReviewRepository
import com.tinuproject.tinu.domain.member.repository.SubEvaluationSummaryRepository
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
import com.tinuproject.tinu.domain.member.service.dto.input.SearchWriteReviewInput
import com.tinuproject.tinu.domain.post.entity.Post
import com.tinuproject.tinu.domain.post.exception.PostNotFoundException
import com.tinuproject.tinu.domain.post.repository.PostRepository
import com.tinuproject.tinu.global.exception.UnauthorizedAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val postRepository : PostRepository,
    private val subEvaluationSummaryRepository: SubEvaluationSummaryRepository
) : ReviewService {

    @Transactional
    override fun createReview(input: CreateReviewInput): Boolean {


        //post 영속화
        val post = postRepository.findPostById(input.postId)?: throw PostNotFoundException()

        //현재 리뷰 작성 요청자가 리뷰 작성 권한이 있는 지 확인.
        if(!validateTradeParticipant(post, input.reviewerId)) throw UnauthorizedAccessException()

        //이미 리뷰를 작성한 적이 있다면 예외 발생
        validateReviewNotExist(input)

        //평가자 피평가자 결정
        val (reviewer, reviewee) = resolveParticipants(post, input.reviewerId)

        //리뷰 이후 평점 반영
        updateRevieweeMark(reviewee, input)

        //리뷰 작성 및 저장
        reviewRepository.save(Review(
            reviewer = reviewer,
            reviewee = reviewee,
            post = post,
            mainEvaluation = input.mainEvaluation,
            isFriendly = input.isFriendly,
            notLate = input.notLate,
            respondedQuickly = input.respondedQuickly
        ))
        
        //SubEvaluation 반영
        updateRevieweeSubEvaluationSummary(reviewee, input)

        return true
    }

    @Transactional(readOnly = true)
    override fun needWrittenReview(input: SearchWriteReviewInput): Boolean {

        val post = postRepository.findPostById(input.postId) ?: throw PostNotFoundException()

        //현재 리뷰 작성자가 작성자 혹은 구매자인지 확인 만약 아니라면
        //리뷰를 작성할 필요가 없으니 false 반환
        if(!validateTradeParticipant(post, input.userId)) return false


        //리뷰를 작성했다면 할필요가 없으니 false, 리뷰를 작성한적 없다면 작성을 해야하니 true
        return !existsReview(input.userId, input.postId)
    }

    private fun existsReview(userId : UUID, postId: Long) : Boolean{
        return reviewRepository.existsByReviewer_UserIdAndPost_Id(reviewerId = userId, postId = postId)
    }

    private fun validateTradeParticipant(post: Post, userId: UUID) : Boolean {
        return !(post.buyer == null || (post.author.userId != userId && post.buyer!!.userId != userId))
    }

    /**
     *     서브 Evaluation 반영을 위한 영속화
     *     만약 이때 첫 리뷰시 = SubEvaluation이 없다면 새로 만들어서 저장.
     *     THINK("Member를 생성하는 시점에 만들어줘야할까 아니면 첫 리뷰 시 만들어줘야할까
     *     리뷰를 작성 받지 않았다 = 거래를 하지 않는 눈팅 유저 가능성 이들에게 데이터를 할당해야하는가?
     *     근데 이렇게 첫 리뷰시 받는다면 앞으로 SubEvaluation을 받을 때 null 체크가 필수
     *     번거롭긴 해도 유저 정보 받아올 때 불필요한 네트워크를 한번 줄일 수 있을 것 같음.
     *     근데 또 코틀린은 null을 그닥 좋아하지 않는 언어인데 언어에게 안맞는 것은 아닌지.")
     */
    private fun updateRevieweeSubEvaluationSummary(
        reviewee: Member,
        input: CreateReviewInput
    ) {
        val subEvaluationSummary = subEvaluationSummaryRepository.findByMemberUserIdForUpdate(reviewee.userId)
            ?: subEvaluationSummaryRepository.save(SubEvaluationSummary(member = reviewee).apply {
                reviewee.subEvaluationSummary = this
            })

        //Dirty 체킹 대신 이를 DB 레벨 단에서 작동하게끔 해도 좋을 것 같음.
        subEvaluationSummary.updateFriendlyNum(input.isFriendly)
        subEvaluationSummary.updateNotLateNum(input.notLate)
        subEvaluationSummary.updateRespondedQuicklyNum(input.respondedQuickly)
    }

    /**
     * 평점 구하는 식은 ((기존 평점 * 반영 전 평가 수) + 새평점) / 반영 후 평가수
     */
    private fun updateRevieweeMark(
        reviewee: Member,
        input: CreateReviewInput
    ) {
        val reviewNum = reviewRepository.countReviewsByReviewee_UserId(revieweeId = reviewee.userId)

        val resultMark = ((reviewee.mark!! * reviewNum) + input.mainEvaluation.score) / (reviewNum + 1)

        reviewee.mark = resultMark
    }

    /**
    *피 평가자를 프론트에서 받는 방식에서 직접 Post에서 추출하는 방식으로 변경.
    *실제 피평가자가 Post 연관자인지 확인할 필요
    *프론트 -> Back으로의 데이터 전송량 감소
    */
    private fun resolveParticipants(post: Post, reviewerId: UUID): Pair<Member, Member> {
        val author = post.author
        val buyer = post.buyer ?: throw UnauthorizedAccessException()

        return if (buyer.userId == reviewerId) {
            buyer to author // (리뷰어, 리뷰이)
        } else {
            author to buyer
        }
    }

    private fun validateReviewNotExist(input: CreateReviewInput) {
        if (existsReview(userId = input.reviewerId, postId = input.postId)) {
            throw ExistReviewException()
        }
    }
}