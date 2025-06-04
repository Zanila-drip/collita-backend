package com.desarrollomovil.backendcollita.services

import com.desarrollomovil.backendcollita.dto.*
import com.desarrollomovil.backendcollita.User
import com.desarrollomovil.backendcollita.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val activeTokens = mutableMapOf<String, String>() // token -> username

    fun login(username: String, password: String): Pair<String, User> {
        val user = userRepository.findByUserUsername(username)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        if (!passwordEncoder.matches(password, user.userPassword)) {
            throw IllegalArgumentException("Contraseña incorrecta")
        }

        // Aquí deberías generar un token JWT real
        val token = "dummy-token-${System.currentTimeMillis()}"
        return Pair(token, user)
    }

    fun logout(token: String) {
        activeTokens.remove(token)
    }

    fun validateToken(token: String): Boolean {
        return activeTokens.containsKey(token)
    }

    fun getUsernameFromToken(token: String): String? {
        return activeTokens[token]
    }

    fun register(registrationDTO: UserRegistrationDTO): User {
        val user = User(
            userUsername = registrationDTO.username,
            email = registrationDTO.email,
            userPassword = registrationDTO.password,
            nombre = registrationDTO.nombre,
            apellidoPaterno = registrationDTO.apellidoPaterno,
            apellidoMaterno = registrationDTO.apellidoMaterno,
            curp = registrationDTO.curp,
            telefono = registrationDTO.telefono
        )
        return userRepository.save(user)
    }
} 