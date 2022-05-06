package com.softhouse.platform

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import javax.persistence.EntityNotFoundException

fun exceptionHandler(exception: Exception): Throwable {
    throw when (exception) {
        is IllegalArgumentException -> ResponseStatusException(
            HttpStatus.BAD_REQUEST, exception.message, exception
        )
        is EntityNotFoundException -> ResponseStatusException(
            HttpStatus.NOT_FOUND, exception.message, exception
        )
        else -> exception
    }
}

fun getUserId( user : String ) : Long {
    var id = kotlin.math.abs( user.hashCode() ).toLong()
    return id
}
