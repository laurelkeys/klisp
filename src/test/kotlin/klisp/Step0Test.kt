package klisp

import klisp.Step0.rep
import org.junit.Test
import kotlin.test.assertEquals

class Step0Test {

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
        val string =
            "hello world abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 (;:() []{}\"'* ;:() []{}\"'* ;:() []{}\"'*)"
        assertEquals(string, rep(string))
    }
}