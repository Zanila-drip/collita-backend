package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.models.Trabajador
import com.desarrollomovil.backendcollita.services.TrabajadorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/trabajadores")
class TrabajadorController(private val trabajadorService: TrabajadorService) {

    @PostMapping
    fun createTrabajador(@RequestBody trabajador: Trabajador): ResponseEntity<Trabajador> {
        return try {
            val createdTrabajador = trabajadorService.createTrabajador(trabajador)
            ResponseEntity.ok(createdTrabajador)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/user/{userId}")
    fun getTrabajadorByUserId(@PathVariable userId: String): ResponseEntity<Trabajador> {
        return trabajadorService.findByUserId(userId)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/categoria/{categoria}")
    fun getTrabajadoresByCategoria(@PathVariable categoria: String): ResponseEntity<List<Trabajador>> {
        val trabajadores = trabajadorService.findByCategoria(categoria)
        return ResponseEntity.ok(trabajadores)
    }

    @GetMapping("/disponibles")
    fun getTrabajadoresDisponibles(): ResponseEntity<List<Trabajador>> {
        val trabajadores = trabajadorService.findDisponibles()
        return ResponseEntity.ok(trabajadores)
    }

    @GetMapping("/categoria/{categoria}/disponibles")
    fun getTrabajadoresByCategoriaAndDisponibles(@PathVariable categoria: String): ResponseEntity<List<Trabajador>> {
        val trabajadores = trabajadorService.findByCategoriaAndDisponible(categoria)
        return ResponseEntity.ok(trabajadores)
    }

    @PutMapping("/{id}/disponibilidad")
    fun updateDisponibilidad(
        @PathVariable id: String,
        @RequestParam disponible: Boolean
    ): ResponseEntity<Trabajador> {
        return try {
            val trabajador = trabajadorService.updateDisponibilidad(id, disponible)
            ResponseEntity.ok(trabajador)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}/calificacion")
    fun updateCalificacion(
        @PathVariable id: String,
        @RequestParam calificacion: Double
    ): ResponseEntity<Trabajador> {
        return try {
            val trabajador = trabajadorService.updateCalificacion(id, calificacion)
            ResponseEntity.ok(trabajador)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}/ubicacion")
    fun updateUbicacion(
        @PathVariable id: String,
        @RequestParam latitud: Double,
        @RequestParam longitud: Double,
        @RequestParam direccion: String
    ): ResponseEntity<Trabajador> {
        return try {
            val trabajador = trabajadorService.updateUbicacion(id, latitud, longitud, direccion)
            ResponseEntity.ok(trabajador)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
} 