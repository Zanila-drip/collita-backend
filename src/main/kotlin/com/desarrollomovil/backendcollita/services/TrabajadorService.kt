package com.desarrollomovil.backendcollita.services

import com.desarrollomovil.backendcollita.models.Trabajador
import com.desarrollomovil.backendcollita.repositories.TrabajadorRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TrabajadorService(
    private val trabajadorRepository: TrabajadorRepository
) {
    fun createTrabajador(trabajador: Trabajador): Trabajador {
        return trabajadorRepository.save(trabajador)
    }

    fun findByUserId(userId: String): Optional<Trabajador> {
        return trabajadorRepository.findByUserId(userId)
    }

    fun findByCategoria(categoria: String): List<Trabajador> {
        return trabajadorRepository.findByCategoria(categoria)
    }

    fun findDisponibles(): List<Trabajador> {
        return trabajadorRepository.findByDisponible(true)
    }

    fun findByCategoriaAndDisponible(categoria: String): List<Trabajador> {
        return trabajadorRepository.findByCategoriaAndDisponible(categoria, true)
    }

    fun updateDisponibilidad(id: String, disponible: Boolean): Trabajador {
        val trabajador = trabajadorRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Trabajador no encontrado") }
        
        return trabajadorRepository.save(trabajador.copy(disponible = disponible))
    }

    fun updateCalificacion(id: String, nuevaCalificacion: Double): Trabajador {
        val trabajador = trabajadorRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Trabajador no encontrado") }
        
        val totalTrabajos = trabajador.totalTrabajos + 1
        val calificacionPromedio = ((trabajador.calificacion * trabajador.totalTrabajos) + nuevaCalificacion) / totalTrabajos
        
        return trabajadorRepository.save(trabajador.copy(
            calificacion = calificacionPromedio,
            totalTrabajos = totalTrabajos
        ))
    }

    fun updateUbicacion(id: String, latitud: Double, longitud: Double, direccion: String): Trabajador {
        val trabajador = trabajadorRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Trabajador no encontrado") }
        
        return trabajadorRepository.save(trabajador.copy(
            ubicacion = trabajador.ubicacion?.copy(
                latitud = latitud,
                longitud = longitud,
                direccion = direccion
            ) ?: com.desarrollomovil.backendcollita.models.Ubicacion(
                latitud = latitud,
                longitud = longitud,
                direccion = direccion
            )
        ))
    }
} 