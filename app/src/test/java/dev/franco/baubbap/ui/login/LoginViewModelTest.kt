package dev.franco.baubbap.ui.login

import dev.franco.baubbap.validator.Curp
import dev.franco.baubbap.validator.InputType
import dev.franco.baubbap.validator.Nip
import dev.franco.baubbap.validator.Validator
import dev.franco.login.helper.ResourceManager
import dev.franco.login.usecase.LoginResult
import dev.franco.login.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.every
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
    private val phoneValidator: Validator = mockk()
    private val curpValidator: Validator = mockk()
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
        coEvery { loginUseCase.login(any(), any()) } returns flow {
            emit(LoginResult.OnSuccess)
        }

        every { curpValidator.validate(any()) } returns Curp.OnValid("")
        every { nipValidator.validate(any()) } returns Nip.OnValid("")
        every { resourceManager.getString(any()) } returns ""

        with(loginViewModel) {
            setUser("cabf890824hmsslr03", InputType.CURP)
            setPassword("0000")
            login()
        }
        assert(loginViewModel.loading)
        advanceTimeBy(1000)
        assert(loginViewModel.loginState is LoginViewState.OnSuccess)
        advanceTimeBy(1000)
        assert(!loginViewModel.loading)
    }

    @Test
    fun whenLoginIsCalledThenReturnErrorLogin() = runTest {
        val errorMessage = "Error!"

        coEvery { loginUseCase.login(any(), any()) } returns flow {
            emit(LoginResult.OnError(20))
        }
        every { curpValidator.validate(any()) } returns Curp.OnValid("")
        every { nipValidator.validate(any()) } returns Nip.OnValid("")

        coEvery { resourceManager.getString(any()) } returns errorMessage

        with(loginViewModel) {
            setUser("cabf890824hmsslr03", InputType.CURP)
            setPassword("0000")
            login()
        }

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
        val password = "0000"

        every { curpValidator.validate(any()) } returns Curp.OnValid("")
        every { nipValidator.validate(any()) } returns Nip.OnValid("")

        with(loginViewModel) {
            setUser("Franco", InputType.CURP)
            setPassword("0000")
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
