package com.desarrollomovil.backendcollita.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user_details")
data class UserPersonalDetails(
    @Id
    val id: String? = null,
    val userId: String,
    val curp: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val telefono: String
) 