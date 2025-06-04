package com.desarrollomovil.backendcollita.services

import com.desarrollomovil.backendcollita.models.User
import com.desarrollomovil.backendcollita.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createUser(user: User): User {
        if (userRepository.existsByUserUsername(user.userUsername)) {
            throw IllegalArgumentException("El nombre de usuario ya está en uso")
        }
        if (userRepository.existsByEmail(user.email)) {
            throw IllegalArgumentException("El email ya está en uso")
        }

        user.userPassword = passwordEncoder.encode(user.userPassword)
        return userRepository.save(user)
    }

    fun findByUsername(username: String): Optional<User> {
        return userRepository.findByUserUsername(username)
    }

    fun findByEmail(email: String): Optional<User> {
        return userRepository.findByEmail(email)
    }

    fun updateUser(id: String, updatedUser: User): User {
        val existingUser = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        if (updatedUser.userUsername != existingUser.userUsername &&
            userRepository.existsByUserUsername(updatedUser.userUsername)) {
            throw IllegalArgumentException("El nombre de usuario ya está en uso")
        }

        if (updatedUser.email != existingUser.email &&
            userRepository.existsByEmail(updatedUser.email)) {
            throw IllegalArgumentException("El email ya está en uso")
        }

        existingUser.apply {
            userUsername = updatedUser.userUsername
            email = updatedUser.email
            if (updatedUser.userPassword.isNotBlank()) {
                userPassword = passwordEncoder.encode(updatedUser.userPassword)
            }
            nombre = updatedUser.nombre
            apellido = updatedUser.apellido
            telefono = updatedUser.telefono
        }

        return userRepository.save(existingUser)
    }

    fun deactivateUser(id: String) {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }
        user.activo = false
        userRepository.save(user)
    }
} 