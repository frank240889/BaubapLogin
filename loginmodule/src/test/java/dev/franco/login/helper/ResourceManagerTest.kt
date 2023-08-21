package dev.franco.login.helper

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class ResourceManagerTest {

    private val context = mockk<Context>()
    private lateinit var resourceManager: ResourceManager

    @Before
    fun setUp() {
        resourceManager = ResourceManagerImpl(context)
    }

    @Test
    fun whenGetStringIsCalledReturnString() {
        val expected = "Hola"
        every { context.getString(any()) } returns expected

        val result = resourceManager.getString(1)

        assert(result == expected)
    }
}
