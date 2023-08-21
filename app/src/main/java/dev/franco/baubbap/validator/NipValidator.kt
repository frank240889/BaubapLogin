package dev.franco.baubbap.validator

import javax.inject.Inject

private const val NIP_LENGTH = 4

class NipValidator @Inject constructor() : Validator {
    override fun validate(input: CharSequence): Result {
        return when {
            input.isEmpty() -> Nip.OnEmpty
            input.length >= NIP_LENGTH -> Nip.OnValid(input)
            else -> Nip.OnInvalid
        }
    }
}

sealed class Nip : Result() {
    class OnValid(val input: CharSequence) : Nip()
    object OnEmpty : Result()
    object OnInvalid : Result()
}
