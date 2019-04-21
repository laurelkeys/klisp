package klisp

import klisp.Printer.printStr
import klisp.Reader.Companion.readStr

object Step1 {

    fun main() {
        while (true) {
            val input = readLine() ?: break // break if null (EOF)
            println("user> ${rep(input)}")
        }
    }

    fun read(string: String) = readStr(string)

    fun eval(string: MalType) = string

    fun print(string: MalType) = printStr(string)

    fun rep(string: String) = print(eval(read(string)))
}

