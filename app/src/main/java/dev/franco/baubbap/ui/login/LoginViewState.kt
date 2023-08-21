package dev.franco.baubbap.ui.login

sealed class LoginViewState(val message: CharSequence = "") {
    class OnSuccess(message: CharSequence) : LoginViewState(message)
    class OnError(message: CharSequence) : LoginViewState(message)
    object Idle : LoginViewState()
}

data class LoginData(
    var curpPhone: CharSequence = "",
    var nip: CharSequence = "",
)
