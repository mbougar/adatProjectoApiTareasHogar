package com.es.adatProjectoApiTareasHogar.dto

import java.util.*

data class TareaDTO(
    val id: String?,
    val titulo: String,
    val desc: String,
    val estado: String,
    val usuario: String,
    val fechCreacion: Date,
    val fechActualizacion: Date
)
