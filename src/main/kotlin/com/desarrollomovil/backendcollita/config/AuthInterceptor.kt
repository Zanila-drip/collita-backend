package com.desarrollomovil.backendcollita.config

import com.desarrollomovil.backendcollita.services.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(private val authService: AuthService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // Permitir acceso a endpoints públicos
        if (isPublicEndpoint(request.requestURI)) {
            return true
        }

        val token = request.getHeader("Authorization")
        if (token == null || !authService.validateToken(token)) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return false
        }

        return true
    }

    private fun isPublicEndpoint(uri: String): Boolean {
        return uri.startsWith("/api/auth/login") ||
               uri.startsWith("/api/users/register") ||
               uri.startsWith("/api/trabajadores/categoria") ||
               uri.startsWith("/api/trabajadores/disponibles")
    }
} 