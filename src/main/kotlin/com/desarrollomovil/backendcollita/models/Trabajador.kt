package com.desarrollomovil.backendcollita.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "trabajadores")
data class Trabajador(
    @Id
    val id: String? = null,
    val userId: String,
    val nombre: String,
    val apellido: String,
    val categoria: String,
    val descripcion: String,
    val tarifa: Double,
    val disponible: Boolean = true,
    val calificacion: Double = 0.0,
    val totalTrabajos: Int = 0,
    val fechaRegistro: LocalDateTime = LocalDateTime.now(),
    val ubicacion: Ubicacion? = null
)

data class Ubicacion(
    val latitud: Double,
    val longitud: Double,
    val direccion: String
) 