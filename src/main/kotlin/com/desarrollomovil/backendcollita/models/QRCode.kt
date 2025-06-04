package com.desarrollomovil.backendcollita.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

enum class QRCodeEstado {
    ACTIVO, INACTIVO, PENDIENTE, CANCELADO
}

@Document(collection = "qrcodes")
data class QRCode(
    @Id
    val id: String,           // Formato: YYYY-MM-DD-XXX
    val fecha: LocalDate,     // Fecha de generación
    val pagoEstimado: Double, // Pago estimado
    val curp: String,         // CURP del usuario
    val estado: QRCodeEstado = QRCodeEstado.ACTIVO,
    val fechaCreacion: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(pagoEstimado > 0) { "El pago estimado debe ser mayor a 0" }
        require(curp.matches(Regex("^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z]{2}$"))) { 
            "El CURP debe tener un formato válido" 
        }
    }
} 