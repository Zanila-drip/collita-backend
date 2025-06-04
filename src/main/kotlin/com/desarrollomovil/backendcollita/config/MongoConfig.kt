package com.desarrollomovil.backendcollita.config

import io.github.cdimascio.dotenv.dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import java.util.concurrent.TimeUnit

@Configuration
class MongoConfig {
    private val dotenv = dotenv {
        directory = "."
        filename = "backend-collita.env"
        ignoreIfMissing = true
    }

    @Bean
    fun mongoClient(): MongoClient {
        val username = dotenv["MONGODB_USERNAME"] ?: throw IllegalStateException("MONGODB_USERNAME no está definido en el archivo backend-collita.env")
        val password = dotenv["MONGODB_PASSWORD"] ?: throw IllegalStateException("MONGODB_PASSWORD no está definido en el archivo backend-collita.env")
        
        val connectionString = "mongodb+srv://$username:$password@cluster0.damyqxk.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
        
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build())
            .applyToSocketSettings { builder ->
                builder.connectTimeout(
                    dotenv["MONGODB_CONNECTION_TIMEOUT"]?.toInt() ?: 5000,
                    TimeUnit.MILLISECONDS
                )
                builder.readTimeout(
                    dotenv["MONGODB_SOCKET_TIMEOUT"]?.toInt() ?: 3000,
                    TimeUnit.MILLISECONDS
                )
            }
            .applyToConnectionPoolSettings { builder ->
                builder.maxSize(dotenv["MONGODB_MAX_POOL_SIZE"]?.toInt() ?: 100)
                builder.minSize(dotenv["MONGODB_MIN_POOL_SIZE"]?.toInt() ?: 10)
            }
            .build()
        
        return MongoClients.create(settings)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {
        val database = dotenv["MONGODB_DATABASE"] ?: "collita"
        return MongoTemplate(SimpleMongoClientDatabaseFactory(mongoClient(), database))
    }
} 