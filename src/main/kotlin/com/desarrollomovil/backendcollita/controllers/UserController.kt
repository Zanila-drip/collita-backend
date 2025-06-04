package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.dto.*
import com.desarrollomovil.backendcollita.models.User
import com.desarrollomovil.backendcollita.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody registrationDTO: UserRegistrationDTO): ResponseEntity<UserResponseDTO> {
        return try {
            val user = User(
                userUsername = registrationDTO.username,
                email = registrationDTO.email,
                userPassword = registrationDTO.password,
                nombre = registrationDTO.nombre,
                apellido = registrationDTO.apellido,
                telefono = registrationDTO.telefono
            )
            val createdUser = userService.createUser(user)
            ResponseEntity.ok(createdUser.toResponseDTO())
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<UserResponseDTO> {
        return userService.findByUsername(username)
            .map { ResponseEntity.ok(it.toResponseDTO()) }
            .orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UserResponseDTO> {
        return userService.findByEmail(email)
            .map { ResponseEntity.ok(it.toResponseDTO()) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: String,
        @RequestBody updatedUser: UserRegistrationDTO
    ): ResponseEntity<UserResponseDTO> {
        return try {
            val user = User(
                userUsername = updatedUser.username,
                email = updatedUser.email,
                userPassword = updatedUser.password,
                nombre = updatedUser.nombre,
                apellido = updatedUser.apellido,
                telefono = updatedUser.telefono
            )
            val updated = userService.updateUser(id, user)
            ResponseEntity.ok(updated.toResponseDTO())
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deactivateUser(@PathVariable id: String): ResponseEntity<Unit> {
        return try {
            userService.deactivateUser(id)
            ResponseEntity.ok().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
}

fun User.toResponseDTO() = UserResponseDTO(
    id = id ?: "",
    username = userUsername,
    email = email,
    nombre = nombre,
    apellido = apellido,
    telefono = telefono,
    rol = rol
) 