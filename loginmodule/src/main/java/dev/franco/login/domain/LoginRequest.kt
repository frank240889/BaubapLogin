package dev.franco.login.domain

data class LoginRequest(
    val user: String,
    val password: String,
)
