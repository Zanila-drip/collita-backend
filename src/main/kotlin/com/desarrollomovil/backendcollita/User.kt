package com.desarrollomovil.backendcollita

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id val id: String? = null,
    var userUsername: String,
    var email: String,
    var userPassword: String,
    var nombre: String,
    var apellidoPaterno: String,
    var apellidoMaterno: String,
    var curp: String,
    var telefono: String,
    var rol: String = "USER"
) 