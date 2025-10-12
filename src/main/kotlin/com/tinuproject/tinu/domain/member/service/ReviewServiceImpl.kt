package com.tinuproject.tinu.domain.member.service

import com.tinuproject.tinu.domain.member.controller.dto.request.SearchReviewInput
import com.tinuproject.tinu.domain.member.entity.Member
import com.tinuproject.tinu.domain.member.entity.Review
import com.tinuproject.tinu.domain.member.entity.SubEvaluationSummary
import com.tinuproject.tinu.domain.member.exception.ExistReviewException
import com.tinuproject.tinu.domain.member.exception.NotExistMemberException
import com.tinuproject.tinu.domain.member.repository.MemberRepository
import com.tinuproject.tinu.domain.member.repository.ReviewRepository
import com.tinuproject.tinu.domain.member.repository.SubEvaluationSummaryRepository
import com.tinuproject.tinu.domain.member.service.dto.input.CreateReviewInput
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
    private val memberRepository: MemberRepository,
    private val postRepository : PostRepository,
    private val subEvaluationSummaryRepository: SubEvaluationSummaryRepository
) : ReviewService {

    @Transactional
    override fun createReview(createReviewInput: CreateReviewInput): Boolean {
        //이미 리뷰를 작성한 적이 있다면 예외 발생
        if(existsReview(userId = createReviewInput.reviewerId, postId = createReviewInput.postId)){
            throw ExistReviewException()
        }

        var reviewer : Member? = null

        var reviewee :Member? = null

        //post 영속화
        val post = postRepository.findPostById(createReviewInput.postId)?: throw PostNotFoundException()

        //현재 리뷰 작성자가 작성자 혹은 구매자인지 확인
        validateTradeParticipant(post, createReviewInput.reviewerId)

        //피 평가자를 프론트에서 받는 방식에서 직접 Post에서 추출하는 방식으로 변경.
        //실제 피평가자가 Post 연관자인지 확인할 필요 X
        //프론트 -> Back으로의 데이터 전송량 감소
        if(post.buyer!=null){
            val author = post.author
            val buyer = post.buyer
            //현재 리뷰작성자가 구매자인 경우
            if(buyer!!.userId == createReviewInput.reviewerId){
                //평가자는 구매자
                //피평가자는 판매자(작성자)
                reviewer = buyer
                reviewee = author
            //리뷰 작성자가 판매자인 경우
            }else {
                //평가자는 판매자,
                //피평가자는 구매자
                reviewer = author
                reviewee = buyer
            }
        //만약 아직 거래가 끝나지 않은 게시글에 대한 것이면 권한 없음.
        }else{
            throw UnauthorizedAccessException()
        }

        val reviewNum = reviewRepository.countReviewsByReviewee_UserId(revieweeId = reviewee.userId)

        val resultMark = ((reviewee.mark!! * reviewNum) + createReviewInput.mainEvaluation.score) / (reviewNum + 1)

        reviewee.mark = resultMark

        //리뷰 작성 및 저장
        reviewRepository.save(Review(
            reviewer = reviewer,
            reviewee = reviewee,
            post = post,
            mainEvaluation = createReviewInput.mainEvaluation,
            isFriendly = createReviewInput.isFriendly,
            notLate = createReviewInput.notLate,
            respondedQuickly = createReviewInput.respondedQuickly
        ))


        //서브 Evaluation 반영을 위한 영속화
        //만약 이때 첫 리뷰시 = SubEvaluation이 없다면 새로 만들어서 저장.
        //THINK("Member를 생성하는 시점에 만들어줘야할까 아니면 첫 리뷰 시 만들어줘야할까")
        //THINK(리뷰를 작성 받지 않았다 = 거래를 하지 않는 눈팅 유저 가능성 이들에게 데이터를 할당해야하는가?
        //  근데 이렇게 첫 리뷰시 받는다면 앞으로 SubEvaluation을 받을 때 null 체크가 필수
        //  번거롭긴 해도 유저 정보 받아올 때 불필요한 네트워크를 한번 줄일 수 있을 것 같음.
        //  근데 또 코틀린은 null을 그닥 좋아하지 않는 언어인데 언어에게 안맞는 것은 아닌지.
        val subEvaluationSummary = subEvaluationSummaryRepository.findByMemberUserIdForUpdate(reviewee.userId)
            ?: subEvaluationSummaryRepository.save(SubEvaluationSummary(member = reviewee).apply {
                reviewee.subEvaluationSummary = this
            })

        //Dirty 체킹 대신 이를 DB 레벨 단에서 작동하게끔 해도 좋을 것 같음.
        subEvaluationSummary.updateFriendlyNum(createReviewInput.isFriendly)
        subEvaluationSummary.updateNotLateNum(createReviewInput.notLate)
        subEvaluationSummary.updateRespondedQuicklyNum(createReviewInput.respondedQuickly)

        return true
    }

    @Transactional(readOnly = true)
    override fun hasWrittenReview(searchReviewInput: SearchReviewInput): Boolean {

        val post = postRepository.findPostById(searchReviewInput.postId) ?: throw PostNotFoundException()

        //현재 리뷰 작성자가 작성자 혹은 구매자인지 확인
        validateTradeParticipant(post, searchReviewInput.userId)


        return existsReview(searchReviewInput.userId, searchReviewInput.postId)
    }

    private fun existsReview(userId : UUID, postId: Long) : Boolean{
        return reviewRepository.existsByReviewer_UserIdAndPost_Id(reviewerId = userId, postId = postId)
    }

    private fun validateTradeParticipant(post: Post, userId: UUID) {
        if(post.buyer == null || (post.author.userId != userId && post.buyer!!.userId != userId)) {
            throw UnauthorizedAccessException()
        }
    }
}