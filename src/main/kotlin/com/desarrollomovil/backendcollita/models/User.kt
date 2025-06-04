package com.desarrollomovil.backendcollita.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "users")
data class User(
    @Id
    var id: String? = null,
    var userUsername: String = "",
    var email: String = "",
    var userPassword: String = "",
    var nombre: String = "",
    var apellido: String = "",
    var telefono: String = "",
    var rol: String = "USER",
    var fechaRegistro: LocalDateTime = LocalDateTime.now(),
    var activo: Boolean = true
) 