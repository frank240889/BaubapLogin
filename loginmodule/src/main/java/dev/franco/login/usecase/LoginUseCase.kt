package dev.franco.login.usecase

import kotlinx.coroutines.flow.Flow

/**
 * Use case for login, handles all logic before making real login, for example
 * validations on inputs, cipher inputs, etc.
 */
interface LoginUseCase {
    suspend fun login(
        user: CharSequence,
        password: CharSequence,
    ): Flow<LoginResult>
}
