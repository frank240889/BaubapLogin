package dev.franco.login.data.source

import dev.franco.login.domain.LoginRequest
import dev.franco.login.domain.LoginResponse
import kotlinx.coroutines.delay
import java.io.IOException
import java.net.ConnectException
import java.util.UUID
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import kotlin.random.Random

private const val DUMMY_DELAY_LOGIN = 3000L
const val INVALID_CREDENTIALS_ERROR = -2
const val SERVER_ERROR = -1
const val CONNECTION_ERROR = -5
const val TIMEOUT_ERROR = -6

class FakeLoginApiImpl @Inject constructor() : LoginApi {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        delay(DUMMY_DELAY_LOGIN)
        val throwException = Random.nextBoolean()
        if (throwException) {
            throw dummyException()
        }
        return dummyResponseFactory()
    }
}

private fun dummyException(): Exception {
    return exceptions[Random.nextInt(0, exceptions.lastIndex)]
}

private fun dummyResponseFactory(): LoginResponse {
    val success = Random.nextBoolean()
    return LoginResponse(
        success = success,
        token = if (success) UUID.randomUUID().toString() else null,
    )
}

private val exceptions = listOf(
    IOException("Server error."),
    ConnectException("Failed to connect to dummy server."),
    TimeoutException("Connection timeout."),
)
