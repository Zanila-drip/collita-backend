package com.desarrollomovil.backendcollita.config

import io.github.cdimascio.dotenv.dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EnvConfig {
    @Bean
    fun dotenv() = dotenv {
        directory = "./"
        ignoreIfMissing = true
    }
} 