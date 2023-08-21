package dev.franco.login.helper

import androidx.annotation.StringRes

/**
 * Access android resources without using context directly.
 */
interface ResourceManager {
    fun getString(@StringRes id: Int): String
}
