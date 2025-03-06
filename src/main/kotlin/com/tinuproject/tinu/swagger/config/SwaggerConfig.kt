package com.tinuproject.tinu.swagger.config

import com.tinuproject.tinu.swagger.annotation.SwaggerErrorResponseByClass
import com.tinuproject.tinu.swagger.annotation.SwaggerErrorResponseByClassAdaptor
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableAspectJAutoProxy
class SwaggerConfig(val swaggerErrorResponseByClassAdaptor: SwaggerErrorResponseByClassAdaptor) :WebMvcConfigurer{


    @Bean
    fun openAPI(): OpenAPI {
        val jwt = "JWT"
        val securityRequirement = SecurityRequirement().addList(jwt)
        val components = Components().addSecuritySchemes(
            jwt, SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        )
        val openAPI = OpenAPI()
            .components(Components())
            .info(apiInfo())
            .addSecurityItem(securityRequirement)
            .components(components)


        return openAPI
    }

    private fun apiInfo(): Info {
        return Info()
            .title("API Test") // API의 제목
            .description("Let's practice Swagger UI") // API에 대한 설명
            .version("1.0.0") // API의 버전
    }

    @Bean
    fun customize(): OperationCustomizer {
        return OperationCustomizer { operation: Operation?, handlerMethod: HandlerMethod ->

            val swaggerErrorResponseByClass : SwaggerErrorResponseByClass? = handlerMethod.getMethodAnnotation(
                SwaggerErrorResponseByClass::class.java
            )


            if(swaggerErrorResponseByClass!=null){
                if(swaggerErrorResponseByClass.errorCodes.size==1){
                    swaggerErrorResponseByClassAdaptor.generateErrorCodeResponseExample(operation!!, swaggerErrorResponseByClass.errorCodes[0] )
                }else{
                    swaggerErrorResponseByClassAdaptor.generateErrorCodeResponseExample(operation!!, swaggerErrorResponseByClass.errorCodes)
                }
            }

            operation
        }
    }
}