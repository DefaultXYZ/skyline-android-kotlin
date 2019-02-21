package com.defaultxyz.skyline.test

import com.defaultxyz.skyline.extensions.or
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class KotlinExtensionsTest {

    @Test
    fun `Return on empty is invoked`() {
        val actual = null?.let { 1 }.or(2)
        assertEquals(2, actual)
    }

    @Test
    fun `Return on empty don't blocks result`() {
        val obj: Any? = Any()
        val actual = obj?.let { 1 }.or(2)
        assertEquals(1, actual)
    }
}