package com.es.adatProjectoApiTareasHogar.error.exception

class NotFoundException (message: String)
    : Exception("Not found exception (404). $message") {

}