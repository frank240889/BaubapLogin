package dev.franco.login.data.source

import dev.franco.login.domain.LoginRequest
import dev.franco.login.domain.LoginResponse

/**
 * Api used to access remote resources.
 */
interface LoginApi {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
}
