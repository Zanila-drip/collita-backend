package com.desarrollomovil.backendcollita.repositories

import com.desarrollomovil.backendcollita.models.Ticket
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TicketRepository : MongoRepository<Ticket, String> {
    fun findByTrabajadorId(trabajadorId: String): List<Ticket>
    fun findByClienteId(clienteId: String): List<Ticket>
    fun findByEstado(estado: String): List<Ticket>
} 