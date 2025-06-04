package com.desarrollomovil.backendcollita.services

import com.desarrollomovil.backendcollita.User
import com.desarrollomovil.backendcollita.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import org.slf4j.LoggerFactory

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun createUser(user: User): User {
        logger.info("Intentando crear usuario: ${user.userUsername}")
        
        if (userRepository.existsByUserUsername(user.userUsername)) {
            logger.error("El nombre de usuario ${user.userUsername} ya está en uso")
            throw IllegalArgumentException("El nombre de usuario ya está en uso")
        }
        if (userRepository.existsByEmail(user.email)) {
            logger.error("El email ${user.email} ya está en uso")
            throw IllegalArgumentException("El email ya está en uso")
        }
        if (userRepository.existsByCurp(user.curp)) {
            logger.error("El CURP ${user.curp} ya está registrado")
            throw IllegalArgumentException("El CURP ya está registrado")
        }

        user.userPassword = passwordEncoder.encode(user.userPassword)
        val savedUser = userRepository.save(user)
        logger.info("Usuario creado exitosamente: ${savedUser.userUsername}")
        return savedUser
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

        if (updatedUser.curp != existingUser.curp &&
            userRepository.existsByCurp(updatedUser.curp)) {
            throw IllegalArgumentException("El CURP ya está registrado")
        }

        existingUser.apply {
            userUsername = updatedUser.userUsername
            email = updatedUser.email
            if (updatedUser.userPassword.isNotBlank()) {
                userPassword = passwordEncoder.encode(updatedUser.userPassword)
            }
            nombre = updatedUser.nombre
            apellidoPaterno = updatedUser.apellidoPaterno
            apellidoMaterno = updatedUser.apellidoMaterno
            curp = updatedUser.curp
            telefono = updatedUser.telefono
        }

        return userRepository.save(existingUser)
    }

    fun deactivateUser(id: String) {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }
        userRepository.delete(user)
    }
} 