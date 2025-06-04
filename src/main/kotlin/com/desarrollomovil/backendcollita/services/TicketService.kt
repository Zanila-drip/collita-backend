package com.desarrollomovil.backendcollita.services

import com.desarrollomovil.backendcollita.models.Ticket
import com.desarrollomovil.backendcollita.repositories.TicketRepository
import org.springframework.stereotype.Service

@Service
class TicketService(private val ticketRepository: TicketRepository) {

    fun createTicket(ticket: Ticket): Ticket {
        return ticketRepository.save(ticket)
    }

    fun getTicketsByTrabajador(trabajadorId: String): List<Ticket> {
        return ticketRepository.findByTrabajadorId(trabajadorId)
    }

    fun getTicketsByCliente(clienteId: String): List<Ticket> {
        return ticketRepository.findByClienteId(clienteId)
    }

    fun getTicketsByEstado(estado: String): List<Ticket> {
        return ticketRepository.findByEstado(estado)
    }

    fun updateTicket(id: String, ticket: Ticket): Ticket {
        val existingTicket = ticketRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Ticket no encontrado") }

        existingTicket.apply {
            estado = ticket.estado
            if (ticket.descripcion.isNotBlank()) {
                descripcion = ticket.descripcion
            }
        }

        return ticketRepository.save(existingTicket)
    }
} 