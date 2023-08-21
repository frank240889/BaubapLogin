package dev.franco.baubbap.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.franco.baubap.R
import dev.franco.baubbap.injection.CurpValidator
import dev.franco.baubbap.injection.NipValidator
import dev.franco.baubbap.injection.PhoneValidator
import dev.franco.baubbap.validator.Curp
import dev.franco.baubbap.validator.InputType
import dev.franco.baubbap.validator.Nip
import dev.franco.baubbap.validator.Result
import dev.franco.baubbap.validator.Validator
import dev.franco.login.data.source.CONNECTION_ERROR
import dev.franco.login.data.source.INVALID_CREDENTIALS_ERROR
import dev.franco.login.data.source.SERVER_ERROR
import dev.franco.login.helper.ResourceManager
import dev.franco.login.usecase.LoginResult
import dev.franco.login.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val resourceManager: ResourceManager,
    @PhoneValidator private val phoneValidator: Validator,
    @CurpValidator private val curpValidator: Validator,
    @NipValidator private val nipValidator: Validator,
) : ViewModel() {
    private var _loginState by mutableStateOf<LoginViewState>(LoginViewState.Idle)
    val loginState: LoginViewState get() = _loginState
    private var _loading by mutableStateOf(false)
    val loading: Boolean get() = _loading
    private var _loginData by mutableStateOf(LoginData())
    val loginData: LoginData get() = _loginData

    var curpValidation by mutableStateOf<Result>(Result.Idle)
    var nipValidation by mutableStateOf<Result>(Result.Idle)

    fun setUser(user: CharSequence, inputType: InputType) {
        _loginData = _loginData.copy(curpPhone = user)
        curpValidation = if (inputType == InputType.CURP) {
            curpValidator.validate(user)
        } else {
            phoneValidator.validate(user)
        }
    }

    fun setPassword(password: CharSequence) {
        _loginData = _loginData.copy(nip = password)
        nipValidation = nipValidator.validate(password)
    }

    fun login() {
        if (loginData.curpPhone.isEmpty()) {
            curpValidation = Curp.OnEmpty
        } else if (loginData.nip.isEmpty()) {
            nipValidation = Nip.OnEmpty
        } else {
            _loading = true
            login(loginData.curpPhone, loginData.nip)
        }
    }

    fun idle() {
        _loginState = LoginViewState.Idle
    }

    private fun login(
        user: CharSequence,
        password: CharSequence,
    ) = viewModelScope.launch(dispatcher) {
        loginUseCase.login(user, password).collect {
            _loginState = getLoginViewStateResult(it)
            _loading = false
        }
    }

    private fun getLoginViewStateResult(it: LoginResult): LoginViewState {
        return if (it is LoginResult.OnSuccess) {
            LoginViewState.OnSuccess(resourceManager.getString(R.string.success_login))
        } else {
            val error = it as LoginResult.OnError
            LoginViewState.OnError(
                when (error.code) {
                    INVALID_CREDENTIALS_ERROR -> {
                        resourceManager.getString(R.string.invalid_credentials)
                    }

                    SERVER_ERROR -> {
                        resourceManager.getString(R.string.server_error)
                    }

                    CONNECTION_ERROR -> {
                        resourceManager.getString(R.string.io_error)
                    }

                    else -> {
                        resourceManager.getString(R.string.timeout_error)
                    }
                },
            )
        }
    }
}
