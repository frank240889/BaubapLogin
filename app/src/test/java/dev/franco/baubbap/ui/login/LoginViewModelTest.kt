package dev.franco.appcompose.ui.login

import dev.franco.baubbap.injection.CurpValidator
import dev.franco.baubbap.injection.NipValidator
import dev.franco.baubbap.injection.PhoneValidator
import dev.franco.baubbap.validator.InputType
import dev.franco.baubbap.validator.Validator
import dev.franco.baubbap.ui.login.LoginViewModel
import dev.franco.baubbap.ui.login.LoginViewState
import dev.franco.login.helper.ResourceManager
import dev.franco.login.usecase.LoginResult
import dev.franco.login.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {
    private val resourceManager = mockk<ResourceManager>()
    private val loginUseCase = mockk<LoginUseCase>()

    @PhoneValidator
    private val phoneValidator: Validator = mockk()

    @CurpValidator
    private val curpValidator: Validator = mockk()

    @NipValidator
    private val nipValidator: Validator = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(
            loginUseCase,
            testDispatcher,
            resourceManager,
            phoneValidator,
            curpValidator,
            nipValidator,
        )
    }

    @Test
    fun whenLoginIsCalledThenReturnSuccessLogin() = runTest {
        val successMessage = "Éxito!"

        coEvery { loginUseCase.login(any(), any()) } returns flow {
            emit(LoginResult.OnSuccess)
        }

        coEvery { resourceManager.getString(any()) } returns successMessage

        loginViewModel.login()
        assert(loginViewModel.loading)
        advanceTimeBy(1000)
        assert(loginViewModel.loginState is LoginViewState.OnSuccess)
        advanceTimeBy(1000)
        assert(!loginViewModel.loading)
        assert(resourceManager.getString(1) == successMessage)
    }

    @Test
    fun whenLoginIsCalledThenReturnErrorLogin() = runTest {
        val errorMessage = "Error!"

        coEvery { loginUseCase.login(any(), any()) } returns flow {
            emit(LoginResult.OnError(20))
        }

        coEvery { resourceManager.getString(any()) } returns errorMessage

        loginViewModel.login()
        assert(loginViewModel.loading)
        advanceTimeBy(1000)
        assert(loginViewModel.loginState is LoginViewState.OnError)
        advanceTimeBy(1000)
        assert(!loginViewModel.loading)
        assert((loginViewModel.loginState as LoginViewState.OnError).message == errorMessage)
    }

    @Test
    fun whenUserTypesUserAndPasswordInternalDataUpdates() {
        val user = "Franco"
        val password = "Omar"

        with(loginViewModel) {
            setUser(user, InputType.CURP)
            setPassword(password)
        }
        assert(loginViewModel.loginData.curpPhone == user)
        assert(loginViewModel.loginData.nip == password)
    }

    @Test
    fun whenLoginIsSuccessfulOrFailedFinalStateIsIdle() = runTest {
        coEvery { loginUseCase.login(any(), any()) } returns flow {
            emit(LoginResult.OnError(20))
        }

        coEvery { loginUseCase.login(any(), any()) } returns flow {
            emit(LoginResult.OnSuccess)
        }

        loginViewModel.login()
        loginViewModel.idle()
        assert(loginViewModel.loginState is LoginViewState.Idle)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }
}
