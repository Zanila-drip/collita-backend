package com.desarrollomovil.backendcollita.services

import com.desarrollomovil.backendcollita.models.User
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

        if (!user.activo) {
            throw IllegalArgumentException("Usuario inactivo")
        }

        if (!passwordEncoder.matches(password, user.userPassword)) {
            throw IllegalArgumentException("Contraseña incorrecta")
        }

        val token = generateToken()
        activeTokens[token] = username
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

    private fun generateToken(): String {
        return UUID.randomUUID().toString()
    }
} 