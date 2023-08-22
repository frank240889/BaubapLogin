package dev.franco.login.domain

data class LoginResponse(
    val success: Boolean,
    val token: String? = null,
)
