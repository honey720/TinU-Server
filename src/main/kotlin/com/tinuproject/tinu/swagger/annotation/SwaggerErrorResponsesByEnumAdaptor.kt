package com.tinuproject.tinu.swagger.annotation

import com.tinuproject.tinu.DTO.ErrorResponse
import com.tinuproject.tinu.DTO.ResponseDTO
import com.tinuproject.tinu.domain.exception.base.ErrorCode
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import org.springframework.stereotype.Component
import java.lang.String
import java.util.function.Consumer
import kotlin.Array
import kotlin.Int


@Component
class SwaggerErrorResponsesByEnumAdaptor {


    fun generateErrorCodeResponseExample(operation :Operation, errorCodes: Array<ErrorCode>){
        val responses: ApiResponses = operation.responses

        val statusWithExampleHolders: Map<Int, MutableList<ExampleHolder>> = errorCodes
            .map { errorCode ->
                ExampleHolder(
                    holder = getSwaggerExample(errorCode),
                    code = errorCode.httpStatusCode,
                    name = errorCode.name
                )
            }
            .groupBy { it.code }.mapValues { it.value.toMutableList() }


        // ExampleHolders를 ApiResponses에 추가
        addExamplesToResponses(responses, statusWithExampleHolders)
    }

    fun generateErrorCodeResponseExample(operation :Operation, errorCode: ErrorCode){
        val responses: ApiResponses = operation.responses


        // ExampleHolder 객체 생성 및 ApiResponses에 추가
        val exampleHolder: ExampleHolder = ExampleHolder(
            holder = getSwaggerExample(errorCode),
            code = errorCode.httpStatusCode,
            name = errorCode.name
        )

        addExamplesToResponses(responses, exampleHolder)
    }


    // ErrorResponseDto 형태의 예시 객체 생성
    private fun getSwaggerExample(errorCode: ErrorCode): Example {
        val errorResponseDto = ResponseDTO(isSuccess = false, stateCode = errorCode.httpStatusCode, result = ErrorResponse(message = errorCode.message, statusCode = errorCode.stateCode))
        val example = Example()
        example.value = errorResponseDto

        return example
    }

    // exampleHolder를 ApiResponses에 추가
    private fun addExamplesToResponses(
        responses: ApiResponses,
        statusWithExampleHolders: Map<Int, MutableList<ExampleHolder>>
    ) {
        statusWithExampleHolders.forEach { (status: Int?, v: MutableList<ExampleHolder>?) ->
            val content: Content = Content()
            val mediaType: MediaType = MediaType()
            val apiResponse: ApiResponse = ApiResponse()

            v.forEach(
                Consumer<ExampleHolder> { exampleHolder: ExampleHolder ->
                    mediaType.addExamples(
                        exampleHolder.name,
                        exampleHolder.holder
                    )
                }
            )
            content.addMediaType("application/json", mediaType)
            apiResponse.setContent(content)
            responses.addApiResponse(status.toString(), apiResponse)
        }
    }

    private fun addExamplesToResponses(responses: ApiResponses, exampleHolder: ExampleHolder) {
        val content: Content = Content()
        val mediaType: MediaType = MediaType()
        val apiResponse: ApiResponse = ApiResponse()

        mediaType.addExamples(exampleHolder.name, exampleHolder.holder)
        content.addMediaType("application/json", mediaType)
        apiResponse.content(content)
        responses.addApiResponse(String.valueOf(exampleHolder.code), apiResponse)
    }
}