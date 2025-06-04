package com.desarrollomovil.backendcollita.repositories

import com.desarrollomovil.backendcollita.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun findByUserUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
    fun findByCurp(curp: String): Optional<User>
    fun existsByUserUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByCurp(curp: String): Boolean
} 