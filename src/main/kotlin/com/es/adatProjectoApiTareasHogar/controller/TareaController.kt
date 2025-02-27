package com.es.adatProjectoApiTareasHogar.controller

import com.es.adatProjectoApiTareasHogar.dto.TareaDTO
import com.es.adatProjectoApiTareasHogar.dto.TareaInsertDTO
import com.es.adatProjectoApiTareasHogar.service.TareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tareas")
class TareaController {

    @Autowired
    private lateinit var tareaService: TareaService

    @PostMapping
    fun crearTarea(@RequestBody tareaInsertDTO: TareaInsertDTO): ResponseEntity<TareaDTO> {
        return ResponseEntity.ok(tareaService.crearTarea(tareaInsertDTO))
    }

    @GetMapping
    fun obtenerTodasTareas(): ResponseEntity<List<TareaDTO>> {
        return ResponseEntity.ok(tareaService.obtenerTodasTareas())
    }

    @GetMapping("/usuario/{usuario}")
    fun obtenerTareasPorUsuario(@PathVariable usuario: String): ResponseEntity<List<TareaDTO>> {
        return ResponseEntity.ok(tareaService.obtenerTareasPorUsuario(usuario))
    }

    @GetMapping("/{id}")
    fun obtenerTareaPorId(@PathVariable id: String): ResponseEntity<TareaDTO> {
        return ResponseEntity.ok(tareaService.obtenerTareaPorId(id))
    }

    @PutMapping("/{id}")
    fun actualizarTarea(@PathVariable id: String, @RequestBody tareaDTO: TareaInsertDTO): ResponseEntity<TareaDTO> {
        return ResponseEntity.ok(tareaService.actualizarTarea(id, tareaDTO))
    }

    @DeleteMapping("/{id}")
    fun eliminarTarea(@PathVariable id: String): ResponseEntity<Void> {
        tareaService.eliminarTarea(id)
        return ResponseEntity.noContent().build()
    }
}