package com.tinuproject.tinu

import com.tinuproject.tinu.domain.common.response.ErrorResponse
import com.tinuproject.tinu.domain.common.response.ResponseDTO
import com.tinuproject.tinu.domain.common.exception.base.BaseException
import com.tinuproject.tinu.domain.common.exception.NotFoundException
import com.tinuproject.tinu.global.response.ResponseEntityGenerator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BaseException::class)
    fun baseException(e : BaseException) : ResponseEntity<ResponseDTO<ErrorResponse>>{
        return ResponseEntityGenerator.onFailure(e)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun noHandlerFoundException(e : NoHandlerFoundException) : ResponseEntity<ResponseDTO<ErrorResponse>>{
        return ResponseEntityGenerator.onFailure(NotFoundException())
    }
}