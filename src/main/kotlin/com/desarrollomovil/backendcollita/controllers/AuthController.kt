package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.dto.*
import com.desarrollomovil.backendcollita.User
import com.desarrollomovil.backendcollita.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {
    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        val (token, user) = authService.login(loginRequest.username, loginRequest.password)
        return ResponseEntity.ok(LoginResponseDTO(token, user.toResponseDTO()))
    }

    @PostMapping("/register")
    fun register(@RequestBody registrationDTO: UserRegistrationDTO): ResponseEntity<LoginResponseDTO> {
        logger.info("Iniciando registro para usuario: ${registrationDTO.username}")
        val (token, user) = authService.register(registrationDTO)
        logger.info("Registro exitoso. Token generado: $token")
        val response = LoginResponseDTO(token, user.toResponseDTO())
        logger.info("Respuesta de registro: $response")
        return ResponseEntity.ok(response)
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<Unit> {
        return try {
            authService.logout(token)
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
} 