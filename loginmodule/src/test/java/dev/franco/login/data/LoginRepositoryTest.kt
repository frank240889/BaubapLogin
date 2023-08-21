package dev.franco.login.data

import app.cash.turbine.test
import dev.franco.login.data.source.LoginApi
import dev.franco.login.domain.LoginRequest
import dev.franco.login.domain.LoginResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.net.ConnectException
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class LoginRepositoryTest {
    private val loginApi: LoginApi = mockk()
    private lateinit var loginRepository: LoginRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginRepository = LoginRepositoryImpl(loginApi)
    }

    @Test
    fun whenLoginIsCalledThenReturnSuccessfulLogin() = runTest {
        // Arrange
        val input = LoginRequest("Franco", "password")
        val expected = LoginResponse(
            success = true,
            token = "mytoken",
        )
        coEvery { loginApi.login(input) } returns
            LoginResponse(
                success = true,
                token = "mytoken",
            )

        // Act
        val flow = loginRepository.login(input)

        // Assert
        flow.test {
            assertEquals(awaitItem(), expected)
            awaitComplete()
        }
    }

    @Test
    fun whenLoginIsCalledThenReturnFailedLogin() = runTest {
        // Arrange
        val input = LoginRequest("Franco", "password")
        val expected = LoginResponse(
            success = true,
            token = "mytoken",
        )
        coEvery { loginApi.login(input) } returns
            LoginResponse(
                success = false,
            )

        // Act
        val flow = loginRepository.login(input)

        // Assert
        flow.test {
            assertNotSame(awaitItem(), expected)
            awaitComplete()
        }
    }

    @Test
    fun whenLoginIsCalledThenExceptionIsThrown() = runTest {
        // Arrange
        val input = LoginRequest("Franco", "password")
        coEvery { loginApi.login(input) }.throws(ConnectException())
        // Act/Assert
        assertThrows<ConnectException> {
            loginRepository.login(input)
        }
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }
}
