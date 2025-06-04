package com.desarrollomovil.backendcollita.repositories

import com.desarrollomovil.backendcollita.models.Trabajador
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TrabajadorRepository : MongoRepository<Trabajador, String> {
    fun findByUserId(userId: String): Optional<Trabajador>
    fun findByCategoria(categoria: String): List<Trabajador>
    fun findByDisponible(disponible: Boolean): List<Trabajador>
    fun findByCategoriaAndDisponible(categoria: String, disponible: Boolean): List<Trabajador>
} 