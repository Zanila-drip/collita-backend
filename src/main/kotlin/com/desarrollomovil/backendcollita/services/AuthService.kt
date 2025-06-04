package com.desarrollomovil.backendcollita.services

import com.desarrollomovil.backendcollita.dto.*
import com.desarrollomovil.backendcollita.User
import com.desarrollomovil.backendcollita.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import org.slf4j.LoggerFactory

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger = LoggerFactory.getLogger(AuthService::class.java)
    private val activeTokens = mutableMapOf<String, String>() // token -> username

    fun login(username: String, password: String): Pair<String, User> {
        val user = userRepository.findByUserUsername(username)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        if (!passwordEncoder.matches(password, user.userPassword)) {
            throw IllegalArgumentException("Contraseña incorrecta")
        }

        // Aquí deberías generar un token JWT real
        val token = "dummy-token-${System.currentTimeMillis()}"
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

    fun register(registrationDTO: UserRegistrationDTO): Pair<String, User> {
        logger.info("Iniciando proceso de registro para usuario: ${registrationDTO.username}")
        
        // Encriptar la contraseña antes de guardar
        val encodedPassword = passwordEncoder.encode(registrationDTO.password)
        logger.info("Contraseña encriptada correctamente")
        
        val user = User(
            userUsername = registrationDTO.username,
            email = registrationDTO.email,
            userPassword = encodedPassword,
            nombre = registrationDTO.nombre,
            apellidoPaterno = registrationDTO.apellidoPaterno,
            apellidoMaterno = registrationDTO.apellidoMaterno,
            curp = registrationDTO.curp,
            telefono = registrationDTO.telefono
        )
        
        logger.info("Guardando usuario en la base de datos")
        val savedUser = userRepository.save(user)
        logger.info("Usuario guardado exitosamente con ID: ${savedUser.id}")
        
        // Generar token después del registro exitoso
        val token = "dummy-token-${System.currentTimeMillis()}"
        activeTokens[token] = savedUser.userUsername
        logger.info("Token generado y guardado para usuario: ${savedUser.userUsername}")
        
        return Pair(token, savedUser)
    }
} 