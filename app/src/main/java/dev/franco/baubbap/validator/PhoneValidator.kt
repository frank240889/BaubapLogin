package dev.franco.baubbap.validator

import javax.inject.Inject

private const val PHONE_LENGTH = 10

class PhoneValidator @Inject constructor() : Validator {
    override fun validate(input: CharSequence): Result {
        return when {
            input.isEmpty() -> Phone.OnEmpty
            input.length == PHONE_LENGTH && input.all { it.isDigit() } ->
                Phone.OnValid(input)

            else -> Phone.OnInvalid
        }
    }
}

sealed class Phone : Result() {
    class OnValid(val input: CharSequence) : Phone()
    object OnEmpty : Phone()
    object OnInvalid : Phone()
}
