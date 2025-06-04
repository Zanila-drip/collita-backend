package com.desarrollomovil.backendcollita

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.desarrollomovil.backendcollita"])
class BackendCollitaApplication

fun main(args: Array<String>) {
    runApplication<BackendCollitaApplication>(*args)
}
