package klisp

import klisp.Step1.rep
import org.junit.Test
import kotlin.test.assertEquals

class Step1Test {

    @Test
    fun testReadOfNumbers() {
        assertEquals("1", rep("1"))
        assertEquals("7", rep("7"))
        assertEquals("7", rep("  7   "))
        assertEquals("-123", rep("-123"))
    }

    @Test
    fun testReadOfSymbols() {
        assertEquals("+", rep("+"))
        assertEquals("abc", rep("abc"))
        assertEquals("abc", rep("   abc   "))
        assertEquals("abc5", rep("abc5"))
        assertEquals("abc-def", rep("abc-def"))
    }

    @Test
    fun testNonNumbersStartingWithADash() {
        assertEquals("-", rep("-"))
        assertEquals("-abc", rep("-abc"))
        assertEquals("->>", rep("->>"))
    }

    @Test
    fun testReadOfLists() {
        assertEquals("(+ 1 2)", rep("(+ 1 2)"))
        assertEquals("()", rep("()"))
        assertEquals("(nil)", rep("(nil)"))
        assertEquals("((3 4))", rep("((3 4))"))
        assertEquals("(+ 1 (+ 2 3))", rep("(+ 1 (+ 2 3))"))
        assertEquals("(+ 1 (+ 2 3))", rep("  ( +   1   (+   2 3   )   )  "))
        assertEquals("(* 1 2)", rep("(* 1 2)"))
        assertEquals("(** 1 2)", rep("(** 1 2)"))
        assertEquals("(* -3 6)", rep("(* -3 6)"))
    }

    @Test
    fun testCommasAsWhitespace() {
        assertEquals("(1 2 3)", rep("(1 2, 3,,,,),,"))
    }

    @Test
    fun testReadOfNil() {
        assertEquals("nil", rep("nil"))
    }

    @Test
    fun testReadOfTrue() {
        assertEquals("true", rep("true"))
    }

    @Test
    fun testReadOfFalse() {
        assertEquals("false", rep("false"))
    }

    @Test
    fun testReadOfStrings() {
        assertEquals("\"abc\"", rep("\"abc\""))
        assertEquals("\"abc\"", rep("   \"abc\"   "))
        assertEquals("\"abc (with parens)\"", rep("\"abc (with parens)\""))
        assertEquals("\"\"", rep("\"\""))
    }
}