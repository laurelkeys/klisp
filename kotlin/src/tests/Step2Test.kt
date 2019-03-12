package tests

import Step2.rep
import org.junit.Test
import kotlin.test.assertEquals

class Step2Test {

    @Test
    fun testingEvaluationOfArithmeticOperations() {
        assertEquals("3", rep("(+ 1 2)"))
        assertEquals("11", rep("(+ 5 (* 2 3))"))
        assertEquals("8", rep("(- (+ 5 (* 2 3)) 3)"))
        assertEquals("2", rep("(/ (- (+ 5 (* 2 3)) 3) 4)"))
        assertEquals("1010", rep("(/ (- (+ 515 (* 87 311)) 302) 27)"))
        assertEquals("-18", rep("(* -3 6)"))
        assertEquals("-994", rep("(/ (- (+ 515 (* -87 311)) 296) 27)"))
    }

    @Test(expected = Exception::class)
    fun testingInvalidInputThrowsException() {
        rep("(abc 1 2 3)")
    }

    @Test
    fun testingEmptyList() {
        assertEquals("()", rep("()"))
    }

}