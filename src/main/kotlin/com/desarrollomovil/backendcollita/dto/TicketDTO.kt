package com.desarrollomovil.backendcollita.dto

import java.time.LocalDateTime

data class TicketCreationDTO(
    val trabajadorId: String,
    val clienteId: String,
    val cosecha: String,
    val ganancia: Double,
    val curpEmpleado: String,
    val descripcion: String,
    val ubicacion: UbicacionDTO
)

data class TicketResponseDTO(
    val id: String,
    val trabajadorId: String,
    val clienteId: String,
    val fecha: LocalDateTime,
    val cosecha: String,
    val ganancia: Double,
    val curpEmpleado: String,
    val estado: String,
    val descripcion: String,
    val ubicacion: UbicacionDTO
)

data class TicketUpdateDTO(
    val estado: String,
    val descripcion: String? = null
) 