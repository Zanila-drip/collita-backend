package com.desarrollomovil.backendcollita.controllers

import com.desarrollomovil.backendcollita.models.User
import com.desarrollomovil.backendcollita.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(private val userService: UserService) {

    @GetMapping("/{username}")
    fun getProfile(@PathVariable username: String): ResponseEntity<User> {
        return userService.findByUsername(username)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/{id}")
    fun updateProfile(
        @PathVariable id: String,
        @RequestBody updatedUser: User
    ): ResponseEntity<User> {
        return try {
            val user = userService.updateUser(id, updatedUser)
            ResponseEntity.ok(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
} 