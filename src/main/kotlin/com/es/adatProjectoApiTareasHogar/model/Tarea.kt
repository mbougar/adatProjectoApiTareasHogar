package com.es.adatProjectoApiTareasHogar.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("Tarea")
data class Tarea(
    @BsonId
    val _id : String?,
    val titulo: String,
    val desc: String,
    val estado: String = "PENDING",
    val usuario: String,
    val fechCreacion: Date,
    val fechActualizacion: Date
)
