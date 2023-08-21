package dev.franco.login.data

import dev.franco.login.data.source.LoginApi
import dev.franco.login.domain.LoginRequest
import dev.franco.login.domain.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
) : LoginRepository {
    override suspend fun login(
        loginRequest: LoginRequest,
    ): Flow<LoginResponse> = flowOf(loginApi.login(loginRequest))
}
