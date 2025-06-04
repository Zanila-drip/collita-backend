package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.dto.*
import com.desarrollomovil.backendcollita.User
import com.desarrollomovil.backendcollita.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(private val userService: UserService) {

    @GetMapping("/{username}")
    fun getProfile(@PathVariable username: String): ResponseEntity<UserResponseDTO> {
        return userService.findByUsername(username)
            .map { ResponseEntity.ok(it.toResponseDTO()) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/{username}")
    fun updateProfile(
        @PathVariable username: String,
        @RequestBody updatedUser: UserRegistrationDTO
    ): ResponseEntity<UserResponseDTO> {
        val existingUser = userService.findByUsername(username)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }
        
        val user = User(
            id = existingUser.id,
            userUsername = updatedUser.username,
            email = updatedUser.email,
            userPassword = updatedUser.password,
            nombre = updatedUser.nombre,
            apellidoPaterno = updatedUser.apellidoPaterno,
            apellidoMaterno = updatedUser.apellidoMaterno,
            curp = updatedUser.curp,
            telefono = updatedUser.telefono,
            rol = existingUser.rol
        )
        
        val updated = userService.updateUser(existingUser.id!!, user)
        return ResponseEntity.ok(updated.toResponseDTO())
    }
} 