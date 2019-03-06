package tests

import org.junit.Test
import rep
import kotlin.test.assertEquals

internal class Step0 {

    @Test
    fun testBasicString() {
        val string = "abcABC123"
        assertEquals(string, rep(string))
    }

    @Test
    fun testStringContainingSpaces() {
        val string = "hello mal world"
        assertEquals(string, rep(string))
    }

    @Test
    fun testStringContainingSymbols() {
        val string = "[]{}\"'* ;:()"
        assertEquals(string, rep(string))
    }

    @Test
    fun testLongString() {
        val string = "hello world abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 (;:() []{}\"'* ;:() []{}\"'* ;:() []{}\"'*)"
        assertEquals(string, rep(string))
    }
}