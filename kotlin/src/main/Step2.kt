package main

import main.Printer.printStr
import main.Reader.Companion.readStr

object Step2 {

    fun main(args: Array<String>) {
        while (true) {
            val input = readLine() ?: break // break if null (EOF)
            println("user> ${rep(input)}")
        }
    }

    fun read(string: String) = readStr(string)

    fun eval(ast: MalType, env: HashMap<String, MalFunction>) =
        when {
            ast !is MalList -> evalAST(ast, env)
            ast.elements.isEmpty() -> ast
            else -> with(evalAST(ast, env)) {
                if (this !is MalList) throw MalException("Expected list not found: $this.")

                val (func, args) = head() to tail()
                if (func !is MalFunction) throw MalException("Expected function not found: $func.")

                func.apply(args)
            }
        }

    private fun evalAST(ast: MalType, env: HashMap<String, MalFunction>): MalType =
        when (ast) {
            is MalSymbol -> env[ast.value] ?: throw MalException("Symbol not found: ${ast.value}.")
            is MalList -> ast.elements.fold(MalList()) { list, elem -> list.apply { add(eval(elem, env)) } }
            else -> ast
        }

    fun print(expr: MalType) = printStr(expr)

    fun rep(string: String): String {
        val replEnv = hashMapOf(
            "+" to MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) + (b as MalNumber) } },
            "-" to MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) - (b as MalNumber) } },
            "*" to MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) * (b as MalNumber) } },
            "/" to MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) / (b as MalNumber) } }
        )

        return print(eval(read(string), replEnv))
    }
}