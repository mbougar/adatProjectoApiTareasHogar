package com.es.adatProjectoApiTareasHogar.service

import com.es.adatProjectoApiTareasHogar.dto.TareaDTO
import com.es.adatProjectoApiTareasHogar.dto.TareaInsertDTO
import com.es.adatProjectoApiTareasHogar.error.exception.BadRequestException
import com.es.adatProjectoApiTareasHogar.error.exception.ForbiddenException
import com.es.adatProjectoApiTareasHogar.error.exception.NotFoundException
import com.es.adatProjectoApiTareasHogar.model.Tarea
import com.es.adatProjectoApiTareasHogar.repository.TareaRepository
import com.es.adatProjectoApiTareasHogar.util.DTOMapper
import com.es.adatProjectoApiTareasHogar.util.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    fun crearTarea(tareaInsertDTO: TareaInsertDTO): TareaDTO {
        if (tareaInsertDTO.titulo.isBlank() || tareaInsertDTO.desc.isBlank()) {
            throw BadRequestException("El título y la descripción son obligatorios")
        }

        val usuarioActual = SecurityContext.getAuthenticatedUser()
        if (!SecurityContext.isAdmin() && tareaInsertDTO.usuario != usuarioActual) {
            throw ForbiddenException("No puedes asignar tareas a otro usuario")
        }

        tareaRepository.findByTituloAndUsuario(tareaInsertDTO.titulo, tareaInsertDTO.usuario).ifPresent {
            throw BadRequestException("El usuario ya tiene una tarea con este título")
        }

        val fechaActual = Date()
        val tarea = Tarea(
            _id = null,
            titulo = tareaInsertDTO.titulo,
            desc = tareaInsertDTO.desc,
            estado = if (tareaInsertDTO.estado.isBlank()) "PENDING" else tareaInsertDTO.estado,
            usuario = tareaInsertDTO.usuario,
            fechCreacion = fechaActual,
            fechActualizacion = fechaActual
        )

        tareaRepository.save(tarea)
        return DTOMapper.entityToTareaDTO(tarea)
    }

    fun obtenerTodasTareas(): List<TareaDTO> {
        val tareas = if (SecurityContext.isAdmin()) tareaRepository.findAll() else tareaRepository.findByUsuario(SecurityContext.getAuthenticatedUser())
        return tareas.map { DTOMapper.entityToTareaDTO(it) }
    }

    fun obtenerTareasPorUsuario(usuario: String): List<TareaDTO> {
        if (!SecurityContext.isAdmin() && usuario != SecurityContext.getAuthenticatedUser()) {
            throw ForbiddenException("No puedes acceder a las tareas de este usuario")
        }
        return tareaRepository.findByUsuario(usuario).map { DTOMapper.entityToTareaDTO(it) }
    }

    fun obtenerTareaPorId(id: String): TareaDTO {
        val tarea = tareaRepository.findById(id).orElseThrow { NotFoundException("No se encontró ninguna tarea con ID: $id") }

        if (!SecurityContext.isAdmin() && tarea.usuario != SecurityContext.getAuthenticatedUser()) {
            throw ForbiddenException("Acceso denegado a esta tarea")
        }

        return DTOMapper.entityToTareaDTO(tarea)
    }

    fun actualizarTarea(id: String, tareaDTO: TareaInsertDTO): TareaDTO {
        val tarea = tareaRepository.findById(id).orElseThrow { NotFoundException("No existe una tarea con el ID: $id") }

        if (!SecurityContext.isAdmin() && tarea.usuario != SecurityContext.getAuthenticatedUser()) {
            throw ForbiddenException("No tienes autorización para cambiar esta tarea")
        }

        val nuevaTarea = tarea.copy(
            titulo = tareaDTO.titulo,
            desc = tareaDTO.desc,
            estado = if (tareaDTO.estado.isBlank()) tarea.estado else tareaDTO.estado,
            fechActualizacion = Date()
        )

        tareaRepository.save(nuevaTarea)
        return DTOMapper.entityToTareaDTO(nuevaTarea)
    }

    fun eliminarTarea(id: String) {
        val tarea = tareaRepository.findById(id).orElseThrow { NotFoundException("No existe ninguna tarea con el ID: $id") }

        if (!SecurityContext.isAdmin() && tarea.usuario != SecurityContext.getAuthenticatedUser()) {
            throw ForbiddenException("No puedes eliminar esta tarea")
        }

        tareaRepository.deleteById(id)
    }
}
