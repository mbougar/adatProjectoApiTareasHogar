package com.es.adatProjectoApiTareasHogar.model

data class Direccion(
    val calle: String,
    val num: String,
    val municipio: String,
    val provincia: String,
    val cp: String,
    val ciudad: String
)