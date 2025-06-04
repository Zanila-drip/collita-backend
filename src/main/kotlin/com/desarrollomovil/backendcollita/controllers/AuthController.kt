package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.dto.LoginRequestDTO
import com.desarrollomovil.backendcollita.dto.LoginResponseDTO
import com.desarrollomovil.backendcollita.dto.UserResponseDTO
import com.desarrollomovil.backendcollita.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        return try {
            val (token, user) = authService.login(loginRequest.username, loginRequest.password)
            ResponseEntity.ok(LoginResponseDTO(token, user.toResponseDTO()))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
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