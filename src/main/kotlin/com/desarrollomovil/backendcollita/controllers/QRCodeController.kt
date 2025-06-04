package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.models.QRCode
import com.desarrollomovil.backendcollita.models.QRCodeEstado
import com.desarrollomovil.backendcollita.services.QRCodeService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/qrcodes")
class QRCodeController(private val qrCodeService: QRCodeService) {

    @PostMapping
    fun generateQRCode(
        @RequestParam pagoEstimado: Double,
        @RequestParam curp: String
    ): ResponseEntity<QRCode> {
        val qrCode = qrCodeService.generateQRCode(pagoEstimado, curp)
        return ResponseEntity.ok(qrCode)
    }

    @GetMapping("/{id}")
    fun getQRCodeById(@PathVariable id: String): ResponseEntity<QRCode> {
        val qrCode = qrCodeService.getQRCodeById(id)
        return if (qrCode != null) {
            ResponseEntity.ok(qrCode)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/curp/{curp}")
    fun getQRCodesByCurp(@PathVariable curp: String): ResponseEntity<List<QRCode>> {
        val qrCodes = qrCodeService.getQRCodesByCurp(curp)
        return ResponseEntity.ok(qrCodes)
    }

    @GetMapping("/fecha/{fecha}")
    fun getQRCodesByFecha(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) fecha: LocalDate
    ): ResponseEntity<List<QRCode>> {
        val qrCodes = qrCodeService.getQRCodesByFecha(fecha)
        return ResponseEntity.ok(qrCodes)
    }

    @PutMapping("/{id}/estado")
    fun updateQRCodeEstado(
        @PathVariable id: String,
        @RequestParam nuevoEstado: QRCodeEstado
    ): ResponseEntity<QRCode> {
        val qrCode = qrCodeService.updateQRCodeEstado(id, nuevoEstado)
        return if (qrCode != null) {
            ResponseEntity.ok(qrCode)
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 