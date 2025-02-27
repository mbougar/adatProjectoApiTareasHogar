package com.es.adatProjectoApiTareasHogar.util

import org.springframework.security.core.context.SecurityContextHolder

object SecurityContext {

    fun getAuthenticatedUser(): String {
        return SecurityContextHolder.getContext().authentication.name
    }

    fun isAdmin(): Boolean {
        return SecurityContextHolder.getContext().authentication.authorities.any { it.authority == "ROLE_ADMIN" }
    }
}