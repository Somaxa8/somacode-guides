package com.somacode.template.config

import com.google.common.base.Predicates
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.*
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Value("\${custom.my-secret-token}") lateinit var mySecretToken: String
    @Value("\${spring.application.name}") lateinit var appName: String
    @Value("\${custom.version:1.0}") lateinit var version: String
    @Value("\${custom.base-url}") lateinit var baseUrl: String
    @Value("\${custom.protocol}") lateinit var protocol: String

    var web = "http://example:8080/"
    var email = "admin@template.com"
    var name = "Template"
    private val restPackage = SwaggerConfig::class.java.getPackage().name.substring(0, SwaggerConfig::class.java.getPackage().name.length - 6) + "controller"


    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(
                    Predicates.or(
                        RequestHandlerSelectors.withClassAnnotation(RestController::class.java)
                    )
                )
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity::class.java)
                .useDefaultResponseMessages(false)
                .securitySchemes(listOf(securitySchema()))
                .securityContexts(listOf(securityContext()))
                .apiInfo(apiInfo())
                .enableUrlTemplating(false)
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title(appName)
                .description("API Documentation")
                .contact(Contact(name, web, email))
                .version(version)
                .build()
    }

    private fun securitySchema(): OAuth {
        val authorizationScopeList = mutableListOf(
                AuthorizationScope("read", "read all"),
                AuthorizationScope("write", "access all")
        )
        val grantTypes = mutableListOf<GrantType>(
                ResourceOwnerPasswordCredentialsGrant("$protocol://$baseUrl/oauth/token")
        )
        return OAuth(appName, authorizationScopeList, grantTypes)
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/**"))
                .build()
    }

    private fun defaultAuth(): List<SecurityReference?> {
        val authorizationScopes = arrayOf(
                AuthorizationScope("read", "read all"),
                AuthorizationScope("write", "write all")
        )
        return listOf(SecurityReference(appName, authorizationScopes))
    }

    @Bean
    fun security(): SecurityConfiguration? {
        return SecurityConfigurationBuilder.builder()
                .clientId(appName)
                .clientSecret(mySecretToken)
                .scopeSeparator(" ")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build()
    }

    @Bean
    fun uiConfig(): UiConfiguration? {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false) // getFacilitiesUsingGET_1
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                .filter(true)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build()
    }

}