package com.es.adatProjectoApiTareasHogar.repository

import com.es.adatProjectoApiTareasHogar.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : MongoRepository<Usuario, String> {

    fun findByUsername(username: String) : Optional<Usuario>
    fun findByEmail(email: String): MutableList<Usuario>
}