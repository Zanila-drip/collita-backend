package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.dto.*
import com.desarrollomovil.backendcollita.User
import com.desarrollomovil.backendcollita.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        val (token, user) = authService.login(loginRequest.username, loginRequest.password)
        return ResponseEntity.ok(LoginResponseDTO(token, user.toResponseDTO()))
    }

    @PostMapping("/register")
    fun register(@RequestBody registrationDTO: UserRegistrationDTO): ResponseEntity<UserResponseDTO> {
        val user = authService.register(registrationDTO)
        return ResponseEntity.ok(user.toResponseDTO())
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