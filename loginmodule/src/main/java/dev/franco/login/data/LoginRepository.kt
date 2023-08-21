package dev.franco.login.data

import dev.franco.login.domain.LoginRequest
import dev.franco.login.domain.LoginResponse
import kotlinx.coroutines.flow.Flow

/**
 * Repository used for login, its responsibility is to access resources either local
 * or remote ones.
 */
interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<LoginResponse>
}
