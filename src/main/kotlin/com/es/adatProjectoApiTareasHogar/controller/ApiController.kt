package com.es.adatProjectoApiTareasHogar.controller

import org.springframework.core.io.Resource
import org.springframework.core.io.ClassPathResource
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class ApiController {

    @GetMapping
    fun getHelloWorld(): ResponseEntity<Resource> {
        val file: Resource = ClassPathResource("static/index.html")
        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_HTML)
            .body(file)
    }
}