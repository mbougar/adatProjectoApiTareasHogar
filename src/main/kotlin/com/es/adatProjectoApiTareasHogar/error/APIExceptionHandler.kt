package com.es.adatProjectoApiTareasHogar.error

import com.es.adatProjectoApiTareasHogar.error.exception.BadRequestException
import com.es.adatProjectoApiTareasHogar.error.exception.ForbiddenException
import com.es.adatProjectoApiTareasHogar.error.exception.NotFoundException
import com.es.adatProjectoApiTareasHogar.error.exception.UnauthorizedException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.naming.AuthenticationException

@ControllerAdvice
class APIExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleBadRequest(request: HttpServletRequest, e: BadRequestException): ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(UnauthorizedException::class, AuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleUnauthorized(request: HttpServletRequest, e: Exception): ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun handleForbidden(request: HttpServletRequest, e: ForbiddenException): ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleNotFound(request: HttpServletRequest, e: NotFoundException): ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleGeneric(request: HttpServletRequest, e: Exception): ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }
}