package dev.franco.login.usecase

import app.cash.turbine.test
import dev.franco.login.data.LoginRepository
import dev.franco.login.domain.LoginRequest
import dev.franco.login.domain.LoginResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginUseCaseTest {
    private val loginRepository = mockk<LoginRepository>()
    private lateinit var loginUseCase: LoginUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginUseCase = LoginUseCaseImpl(loginRepository)
    }

    @Test
    fun whenLoginIsInvokedThenReturnSuccess() = runTest {
        val user = "Franco"
        val password = "password"
        val request = LoginRequest(
            user = user,
            password = password,
        )

        coEvery { loginRepository.login(request) } returns flow {
            emit(
                LoginResponse(
                    success = true,
                    token = "token",
                ),
            )
        }

        loginUseCase
            .login(user, password)
            .test {
                assert(awaitItem() is LoginResult.OnSuccess)
                awaitComplete()
            }
    }

    @Test
    fun whenLoginIsInvokedThenReturnError() = runTest {
        val user = "Franco"
        val password = "password"
        val request = LoginRequest(
            user = user,
            password = password,
        )

        coEvery { loginRepository.login(request) } returns flow {
            emit(
                LoginResponse(
                    success = false,
                ),
            )
        }

        loginUseCase
            .login(user, password)
            .test {
                assert(awaitItem() is LoginResult.OnError)
                awaitComplete()
            }
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }
}
