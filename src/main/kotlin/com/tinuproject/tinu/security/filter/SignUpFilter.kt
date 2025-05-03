package com.tinuproject.tinu.security.filter

import com.tinuproject.tinu.tempdomain.member.exception.NeedRegistException
import com.tinuproject.tinu.security.jwt.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class SignUpFilter(

    private val jwtUtil :JwtUtil,

    //토큰이 없어도 되는 api
    private val excludeUrls : List<String>

): OncePerRequestFilter(){


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        var httpServeletRequest : HttpServletRequest = request

        var accessToken : String = jwtUtil.getTokenFromHeader(httpServeletRequest)

        if(!jwtUtil.signCheck(accessToken)){
            throw NeedRegistException()
        }


        filterChain.doFilter(request,response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {

        return excludeUrls.stream().anyMatch {
            request.servletPath.contains(it)
        }||request.servletPath.contains("/api/register")
    }
}