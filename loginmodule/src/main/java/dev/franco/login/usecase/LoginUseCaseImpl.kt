package dev.franco.login.usecase

import dev.franco.login.data.LoginRepository
import dev.franco.login.data.source.CONNECTION_ERROR
import dev.franco.login.data.source.INVALID_CREDENTIALS_ERROR
import dev.franco.login.data.source.SERVER_ERROR
import dev.franco.login.data.source.TIMEOUT_ERROR
import dev.franco.login.domain.LoginRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository,
) : LoginUseCase {
    override suspend fun login(
        user: CharSequence,
        password: CharSequence,
    ): Flow<LoginResult> {
        return try {
            loginRepository
                .login(
                    LoginRequest(
                        user = user.toString(),
                        password = password.toString(),
                    ),
                )
                .map {
                    if (it.success) {
                        LoginResult.OnSuccess
                    } else {
                        LoginResult.OnError(INVALID_CREDENTIALS_ERROR)
                    }
                }
        } catch (e: TimeoutException) {
            flow { emit(LoginResult.OnError(TIMEOUT_ERROR)) }
        } catch (e: ConnectException) {
            flow { emit(LoginResult.OnError(CONNECTION_ERROR)) }
        } catch (e: IOException) {
            flow { emit(LoginResult.OnError(SERVER_ERROR)) }
        }
    }
}
