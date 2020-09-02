package com.zukron.kalkulator

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
class ExampleUnitTest {
    @Test
    fun test() {
        val text = "233+323/232"
        val textArray = text.split(Regex("[x/+-]"))

        for (t in textArray) {
            println(t)
        }
    }
}