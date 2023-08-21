package dev.franco.login.usecase

sealed class LoginResult {
    object OnSuccess : LoginResult()
    class OnError(val code: Int) : LoginResult()
}
