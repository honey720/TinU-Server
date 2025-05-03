package com.tinuproject.tinu.tempdomain.member.entity

import com.tinuproject.tinu.tempdomain.common.entity.BaseEntity
import com.tinuproject.tinu.tempdomain.member.socialmember.enums.Social
import com.tinuproject.tinu.tempdomain.member.dto.input.UpdateUserInputDTO
import com.tinuproject.tinu.tempdomain.common.university.entity.University
import com.tinuproject.tinu.tempdomain.member.customfilter.entity.CustomFilter
import com.tinuproject.tinu.tempdomain.member.inquiry.entity.Inquiry
import com.tinuproject.tinu.tempdomain.post.chat.entity.Chat
import com.tinuproject.tinu.tempdomain.post.entity.Post
import com.tinuproject.tinu.tempdomain.post.scrap.entity.Scrap
import jakarta.persistence.*
import java.util.*

@Entity
class Member (

    @Column(columnDefinition = "BINARY(16)", unique = true)
    var userId : UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="university_id")
    var university: University? = null,

    @Column
    var nickname : String?,

    @Column
    var major : String?,

    @Column
    var grade : Int?,

    @Column
    var profileImageURL : String?,

    @Column
    var introduction : String?,

    @Column
    var email : String?,

    @Column
    var reportCount : Long=0,

    @Column
    var mark : Double?,

    @Column
    @Enumerated(EnumType.ORDINAL)
    var social : Social,

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "author"
    )
    var post : MutableList<Post> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "member")
    var scrap : MutableList<Scrap> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "buyer")
    var buyerChat : MutableList<Chat> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "seller")
    var sellerChat : MutableList<Chat> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "inquirer")
    var inquiry: MutableList<Inquiry> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
        mappedBy = "member")
    var customFilter : MutableList<CustomFilter> = mutableListOf()
) : BaseEntity(){

    fun updateMemberInfo(updateUserInputDTO: UpdateUserInputDTO){
        this.nickname = updateUserInputDTO.nickname
        this.grade = updateUserInputDTO.grade
        this.major = updateUserInputDTO.major
        this.introduction = updateUserInputDTO.introduction
        this.profileImageURL = updateUserInputDTO.profile
    }

}