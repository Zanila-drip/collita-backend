package com.desarrollomovil.backendcollita.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "tickets")
data class Ticket(
    @Id
    var id: String? = null,
    var trabajadorId: String = "",
    var clienteId: String = "",
    var fecha: LocalDateTime = LocalDateTime.now(),
    var cosecha: String = "",
    var ganancia: Double = 0.0,
    var curpEmpleado: String = "",
    var estado: String = "PENDIENTE", // PENDIENTE, COMPLETADO, CANCELADO
    var descripcion: String = "",
    var ubicacion: Ubicacion? = null
) 