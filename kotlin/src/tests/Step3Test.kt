package tests

import main.*
import main.Step3.rep
import org.junit.Test
import kotlin.test.assertEquals

class Step3Test {

    private val env = Env(outer = null).apply {
        set(MalSymbol("+"),
            MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) + (b as MalNumber) } })
        set(MalSymbol("-"),
            MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) - (b as MalNumber) } })
        set(MalSymbol("*"),
            MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) * (b as MalNumber) } })
        set(MalSymbol("/"),
            MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) / (b as MalNumber) } })
    }

    @Test
    fun testingReplEnv() {
        assertEquals("3", rep("(+ 1 2)", env))
        assertEquals("2", rep("(/ (- (+ 5 (* 2 3)) 3) 4)", env))
    }

    @Test
    fun testingDef() {
        assertEquals("3", rep("(def! x 3)", env))
        assertEquals("3", rep("x", env))
        assertEquals("4", rep("(def! x 4)", env))
        assertEquals("4", rep("x", env))
        assertEquals("8", rep("(def! y (+ 1 7))", env))
        assertEquals("8", rep("y", env))
    }

    @Test
    fun verifyingSymbolsAreCaseSensitive() {
        assertEquals("111", rep("(def! mynum 111)", env))
        assertEquals("222", rep("(def! MYNUM 222)", env))
        assertEquals("111", rep("mynum", env))
        assertEquals("222", rep("MYNUM", env))
    }

    @Test
    fun testingLet() {
        assertEquals("9", rep("(let* (z 9) z)", env))
        assertEquals("9", rep("(let* (x 9) x)", env))
        assertEquals("6", rep("(let* (z (+ 2 3)) (+ 1 z))", env))
        assertEquals("12", rep("(let* (p (+ 2 3) q (+ 2 p)) (+ p q))", env))
        assertEquals("7", rep("(def! y (let* (z 7) z))", env))
        assertEquals("7", rep("y", env))
    }

    @Test
    fun testingOuterEnvironment() {
        assertEquals("4", rep("(def! a 4)", env))
        assertEquals("9", rep("(let* (q 9) q)", env))
        assertEquals("4", rep("(let* (q 9) a)", env))
        assertEquals("4", rep("(let* (z 2) (let* (q 9) a))", env))
    }
}