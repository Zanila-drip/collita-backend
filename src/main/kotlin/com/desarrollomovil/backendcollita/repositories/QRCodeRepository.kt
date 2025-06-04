package com.desarrollomovil.backendcollita.repositories

import com.desarrollomovil.backendcollita.models.QRCode
import com.desarrollomovil.backendcollita.models.QRCodeEstado
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDate

interface QRCodeRepository : MongoRepository<QRCode, String> {
    @Query("{ 'fecha': ?0 }")
    fun findByFecha(fecha: LocalDate): List<QRCode>

    @Query("{ 'curp': ?0 }")
    fun findByCurp(curp: String): List<QRCode>

    @Query("{ 'estado': ?0 }")
    fun findByEstado(estado: QRCodeEstado): List<QRCode>
} 