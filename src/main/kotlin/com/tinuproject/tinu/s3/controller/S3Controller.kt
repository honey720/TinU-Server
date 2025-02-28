package com.tinuproject.tinu.s3.controller

import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.s3.dto.request.*
import com.tinuproject.tinu.s3.service.S3Service
import com.tinuproject.tinu.web.ResponseEntityGenerator
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/image")
class S3Controller(
        private val s3Service: S3Service,
) {

    @PostMapping("/initiateUpload")
    fun initiateUpload(@RequestBody s3UploadInitiateRequest: S3UploadInitiateRequest): ResponseEntity<ResponseDTO> {

        val responseDTO = ResponseDTO(
                isSuccess = true,
                stateCode = 200,
                result = s3Service.initiateUpload(s3UploadInitiateRequest)
        )

        return ResponseEntity.ok().body(responseDTO)
    }

    @PostMapping("/presigned-url")
    fun getUploadPresignedUrl(@AuthenticationPrincipal userId: UUID, @RequestBody s3PresignedUrlRequest: S3PresignedUrlRequest): ResponseEntity<ResponseDTO> {

        return ResponseEntityGenerator.onSuccess(s3Service.getPreSignedUrl(s3PresignedUrlRequest))
    }

    @PostMapping("/complete-upload")
    fun completeUpload(@RequestBody s3UploadCompleteRequest: S3UploadCompleteRequest): ResponseEntity<ResponseDTO> {

        val responseDTO = ResponseDTO(
                isSuccess = true,
                stateCode = 200,
                result = s3Service.completeUpload(s3UploadCompleteRequest)
        )

        return ResponseEntity.ok().body(responseDTO)
    }

    @PostMapping("/abort-upload")
    fun abortUpload(@RequestBody s3UploadAbortRequest: S3UploadAbortRequest): ResponseEntity<ResponseDTO> {

        val responseDTO = ResponseDTO(
                isSuccess = true,
                stateCode = 200,
                result = s3Service.abortUpload(s3UploadAbortRequest)
        )

        return ResponseEntity.ok().body(responseDTO)
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody s3DeleteRequest: S3DeleteRequest): ResponseEntity<ResponseDTO> {

        val responseDTO = ResponseDTO(
                isSuccess = true,
                stateCode = 200,
                result = s3Service.deleteObject(s3DeleteRequest)
        )

        return ResponseEntity.ok().body(responseDTO)
    }
}