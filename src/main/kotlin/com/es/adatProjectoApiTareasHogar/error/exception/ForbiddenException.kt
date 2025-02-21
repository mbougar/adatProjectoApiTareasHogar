package com.es.adatProjectoApiTareasHogar.error.exception

class ForbiddenException(message: String) : Exception("Forbidden Exception(403). $message") {
}