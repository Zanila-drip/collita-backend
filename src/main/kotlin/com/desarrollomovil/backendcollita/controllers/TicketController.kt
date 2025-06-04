package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.dto.*
import com.desarrollomovil.backendcollita.models.Ticket
import com.desarrollomovil.backendcollita.services.TicketService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tickets")
class TicketController(private val ticketService: TicketService) {

    @PostMapping
    fun createTicket(@RequestBody ticketDTO: TicketCreationDTO): ResponseEntity<TicketResponseDTO> {
        val ticket = Ticket(
            trabajadorId = ticketDTO.trabajadorId,
            clienteId = ticketDTO.clienteId,
            cosecha = ticketDTO.cosecha,
            ganancia = ticketDTO.ganancia,
            curpEmpleado = ticketDTO.curpEmpleado,
            descripcion = ticketDTO.descripcion,
            ubicacion = com.desarrollomovil.backendcollita.models.Ubicacion(
                latitud = ticketDTO.ubicacion.latitud,
                longitud = ticketDTO.ubicacion.longitud,
                direccion = ticketDTO.ubicacion.direccion
            )
        )
        val createdTicket = ticketService.createTicket(ticket)
        return ResponseEntity.ok(createdTicket.toResponseDTO())
    }

    @GetMapping("/trabajador/{trabajadorId}")
    fun getTicketsByTrabajador(@PathVariable trabajadorId: String): ResponseEntity<List<TicketResponseDTO>> {
        val tickets = ticketService.getTicketsByTrabajador(trabajadorId)
        return ResponseEntity.ok(tickets.map { it.toResponseDTO() })
    }

    @GetMapping("/cliente/{clienteId}")
    fun getTicketsByCliente(@PathVariable clienteId: String): ResponseEntity<List<TicketResponseDTO>> {
        val tickets = ticketService.getTicketsByCliente(clienteId)
        return ResponseEntity.ok(tickets.map { it.toResponseDTO() })
    }

    @GetMapping("/estado/{estado}")
    fun getTicketsByEstado(@PathVariable estado: String): ResponseEntity<List<TicketResponseDTO>> {
        val tickets = ticketService.getTicketsByEstado(estado)
        return ResponseEntity.ok(tickets.map { it.toResponseDTO() })
    }

    @PutMapping("/{id}")
    fun updateTicket(
        @PathVariable id: String,
        @RequestBody updateDTO: TicketUpdateDTO
    ): ResponseEntity<TicketResponseDTO> {
        val ticket = Ticket(
            estado = updateDTO.estado,
            descripcion = updateDTO.descripcion ?: ""
        )
        val updatedTicket = ticketService.updateTicket(id, ticket)
        return ResponseEntity.ok(updatedTicket.toResponseDTO())
    }
}

private fun Ticket.toResponseDTO() = TicketResponseDTO(
    id = id ?: "",
    trabajadorId = trabajadorId,
    clienteId = clienteId,
    fecha = fecha,
    cosecha = cosecha,
    ganancia = ganancia,
    curpEmpleado = curpEmpleado,
    estado = estado,
    descripcion = descripcion,
    ubicacion = ubicacion?.let {
        UbicacionDTO(
            latitud = it.latitud,
            longitud = it.longitud,
            direccion = it.direccion
        )
    } ?: UbicacionDTO(0.0, 0.0, "")
) 