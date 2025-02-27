package com.es.adatProjectoApiTareasHogar.dto

data class TareaInsertDTO(
    val titulo: String,
    val desc: String,
    val estado: String = "PENDING",
    val usuario: String
)
