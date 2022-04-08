package com.somacode.template.config

import com.somacode.template.config.exception.*
import org.springframework.beans.NotReadablePropertyException
import org.springframework.context.annotation.Configuration
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpServerErrorException
import java.io.IOException
import java.time.format.DateTimeParseException
import javax.naming.ServiceUnavailableException
import javax.validation.ConstraintViolationException

@Configuration
@ControllerAdvice(annotations = [RestController::class])
class ExceptionConfig {


    @ExceptionHandler(
            BadRequestException::class,
            DataIntegrityViolationException::class,
            InvalidGrantException::class,
            RequestRejectedException::class,
            IllegalArgumentException::class,
            ConstraintViolationException::class,
            DateTimeParseException::class
    )
    fun badRequestException(e: Exception): ResponseEntity<*>? {
//        val log: Log = LogService.out.info(e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<Any>(MessageError(e.message))
    }

    @ExceptionHandler(ConflictException::class)
    fun conflictException(e: Exception): ResponseEntity<*>? {
//        val log: Log = LogService.out.info(e)
        return ResponseEntity.status(HttpStatus.CONFLICT).body<Any>(MessageError(e.message))
    }

    @ExceptionHandler(NullPointerException::class, NotReadablePropertyException::class, IOException::class)
    fun internalServerErrorException(e: Exception): ResponseEntity<*>? {
//        val log: Log = LogService.out.error(e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body<Any>(MessageError(e.message))
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(e: Exception): ResponseEntity<*>? {
//        val log: Log = LogService.out.info(e)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(MessageError(e.message))
    }

    @ExceptionHandler(ForbiddenException::class)
    fun forbiddenException(e: Exception): ResponseEntity<*>? {
//        val log: Log = LogService.out.info(e)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body<Any>(MessageError(e.message))
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorizedException(e: Exception): ResponseEntity<*>? {
//        val log: Log = LogService.out.info(e)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body<Any>(MessageError(e.message))
    }

    @ExceptionHandler(ServiceUnavailableException::class)
    fun serviceUnavailableException(e: Exception): ResponseEntity<*>? {
//        val log: Log = LogService.out.info(e)
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body<Any>(MessageError(e.message))
    }

}