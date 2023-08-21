package dev.franco.login.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ResourceManager {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}
