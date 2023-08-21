package dev.franco.baubbap.validator

import javax.inject.Inject

private const val CURP_LENGTH = 18
private const val CURP_REGEX =
    """[A-Z]{4}[0-9]{6}[HM]{1}[A-Z]{2}[BCDFGHJKLMNPQRSTVWXYZ]{3}([A-Z]{2})?([0-9]{2})?"""

class CurpValidator @Inject constructor() : Validator {
    private val curpRegex = CURP_REGEX.toRegex()
    override fun validate(input: CharSequence): Result {
        return when {
            input.isEmpty() -> Curp.OnEmpty
            input.length == CURP_LENGTH && curpRegex.matches(input) -> Curp.OnValid(input)
            else -> Curp.OnInvalid
        }
    }
}

sealed class Curp : Result() {
    class OnValid(val input: CharSequence) : Curp()
    object OnEmpty : Curp()
    object OnInvalid : Curp()
}
