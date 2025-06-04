package com.desarrollomovil.backendcollita.dto

data class UserRegistrationDTO(
    val username: String,
    val email: String,
    val password: String,
    val nombre: String,
    val apellido: String,
    val telefono: String
)

data class UserResponseDTO(
    val id: String,
    val username: String,
    val email: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val rol: String
)

data class LoginRequestDTO(
    val username: String,
    val password: String
)

data class LoginResponseDTO(
    val token: String,
    val user: UserResponseDTO
) 