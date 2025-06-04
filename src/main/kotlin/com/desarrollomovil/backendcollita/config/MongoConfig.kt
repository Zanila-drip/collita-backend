package com.desarrollomovil.backendcollita.config

import io.github.cdimascio.dotenv.dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
class MongoConfig {
    private val dotenv = dotenv {
        directory = "."
        ignoreIfMissing = true
    }

    @Bean
    fun mongoClient(): MongoClient {
        val username = dotenv["MONGODB_USERNAME"] ?: "Zanila"
        val password = dotenv["MONGODB_PASSWORD"] ?: "salsita123"
        val connectionString = "mongodb+srv://$username:$password@damyqxk.mongodb.net/collita?retryWrites=true&w=majority&authSource=admin"
        
        return MongoClients.create(connectionString)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(SimpleMongoClientDatabaseFactory(mongoClient(), "collita"))
    }
} 