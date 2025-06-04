package com.desarrollomovil.backendcollita.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.slf4j.LoggerFactory

@Configuration
class MongoConnectionTest {
    private val logger = LoggerFactory.getLogger(MongoConnectionTest::class.java)

    @Bean
    fun testMongoConnection(mongoTemplate: MongoTemplate): CommandLineRunner {
        return CommandLineRunner {
            try {
                // Intentar obtener la lista de colecciones
                val collections = mongoTemplate.collectionNames
                logger.info("Conexión a MongoDB exitosa!")
                logger.info("Colecciones disponibles: $collections")
            } catch (e: Exception) {
                logger.error("Error al conectar con MongoDB: ${e.message}")
                e.printStackTrace()
            }
        }
    }
} 