package com.desarrollomovil.backendcollita.dto

data class TrabajadorRegistrationDTO(
    val userId: String,
    val nombre: String,
    val apellido: String,
    val categoria: String,
    val descripcion: String,
    val tarifa: Double,
    val ubicacion: UbicacionDTO
)

data class TrabajadorResponseDTO(
    val id: String,
    val userId: String,
    val nombre: String,
    val apellido: String,
    val categoria: String,
    val descripcion: String,
    val tarifa: Double,
    val disponible: Boolean,
    val calificacion: Double,
    val totalTrabajos: Int,
    val ubicacion: UbicacionDTO
)

data class UbicacionDTO(
    val latitud: Double,
    val longitud: Double,
    val direccion: String
)

data class TrabajadorDisponibilidadDTO(
    val disponible: Boolean
)

data class TrabajadorCalificacionDTO(
    val calificacion: Double
)

data class TrabajadorUbicacionDTO(
    val latitud: Double,
    val longitud: Double,
    val direccion: String
) 