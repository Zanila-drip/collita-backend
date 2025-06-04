package com.desarrollomovil.backendcollita.services

import com.desarrollomovil.backendcollita.models.QRCode
import com.desarrollomovil.backendcollita.models.QRCodeEstado
import com.desarrollomovil.backendcollita.repositories.QRCodeRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class QRCodeService(private val qrCodeRepository: QRCodeRepository) {

    fun generateQRCode(pagoEstimado: Double, curp: String): QRCode {
        val fecha = LocalDate.now()
        val id = generateUniqueId(fecha)
        
        val qrCode = QRCode(
            id = id,
            fecha = fecha,
            pagoEstimado = pagoEstimado,
            curp = curp
        )
        
        return qrCodeRepository.save(qrCode)
    }

    private fun generateUniqueId(fecha: LocalDate): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateStr = fecha.format(dateFormatter)
        
        // Obtener el último número secuencial del día
        val qrCodesDelDia = qrCodeRepository.findByFecha(fecha)
        val ultimoNumero = qrCodesDelDia
            .map { it.id.split("-").last().toIntOrNull() ?: 0 }
            .maxOrNull() ?: 0
        
        // Generar el nuevo número secuencial
        val nuevoNumero = String.format("%03d", ultimoNumero + 1)
        
        return "$dateStr-$nuevoNumero"
    }

    fun getQRCodeById(id: String): QRCode? {
        return qrCodeRepository.findById(id).orElse(null)
    }

    fun getQRCodesByCurp(curp: String): List<QRCode> {
        return qrCodeRepository.findByCurp(curp)
    }

    fun getQRCodesByFecha(fecha: LocalDate): List<QRCode> {
        return qrCodeRepository.findByFecha(fecha)
    }

    fun updateQRCodeEstado(id: String, nuevoEstado: QRCodeEstado): QRCode? {
        val qrCode = qrCodeRepository.findById(id).orElse(null) ?: return null
        val qrCodeActualizado = qrCode.copy(estado = nuevoEstado)
        return qrCodeRepository.save(qrCodeActualizado)
    }
} 