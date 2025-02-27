package com.es.adatProjectoApiTareasHogar.repository

import com.es.adatProjectoApiTareasHogar.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TareaRepository : MongoRepository<Tarea, String> {
    fun findByUsuario(usuario: String): List<Tarea>
    fun findByTituloAndUsuario(titulo: String, usuario: String): Optional<Tarea>
}