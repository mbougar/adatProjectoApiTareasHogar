package com.es.adatProjectoApiTareasHogar

import com.es.adatProjectoApiTareasHogar.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class AdatProjectoApiTareasHogarApplication

fun main(args: Array<String>) {
	runApplication<AdatProjectoApiTareasHogarApplication>(*args)
}
