package dev.franco.baubbap.validator

/**
 * A generalization for validations of text inputs.
 */
interface Validator {
    fun validate(input: CharSequence): Result
}

sealed class Result {
    object Idle : Result()
}
