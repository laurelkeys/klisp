package main

object Step3 {

    fun main(args: Array<String>) {

        val env = Env(outer = null).apply {
            set(MalSymbol("+"),
                MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) + (b as MalNumber) } })
            set(MalSymbol("-"),
                MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) - (b as MalNumber) } })
            set(MalSymbol("*"),
                MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) * (b as MalNumber) } })
            set(MalSymbol("/"),
                MalFunction { list: MalList -> list.elements.reduce { a, b -> (a as MalNumber) / (b as MalNumber) } })
        }

        while (true) {
            val input = readLine() ?: break // break if null (EOF)
            println("user> ${rep(input, env)}")
        }
    }

    fun read(string: String) = Reader.readStr(string)

    fun eval(ast: MalType, env: Env): MalType {
        when {
            ast !is MalList -> return evalAST(ast, env)
            ast.elements.isEmpty() -> return ast
            ast.isSpecialForm("def!") -> {
                val (key, value) = with(ast.tail()) { head() to tail().head() }
                return env.set(key as MalSymbol, eval(value, env))
            }
            ast.isSpecialForm("let*") -> {
                val (bindings, form) = with(ast.tail()) { head() to tail().head() }
                val childEnv = Env(outer = env)

                if (bindings !is MalList)
                    throw MalException("List expected as the first parameter of let*. Received: $bindings.")

                bindings.elements.iterator().let {
                    while (it.hasNext()) {
                        val key = it.next()
                        if (!it.hasNext())
                            throw MalException("Even number of elements expected on let* call. Received: ${bindings.elements}.")
                        childEnv.set(key as MalSymbol, eval(it.next(), childEnv))
                    }
                }

                return eval(form, childEnv)
            }
            else -> {
                with(evalAST(ast, env)) {
                    if (this !is MalList) throw MalException("Expected list not found: $this.")
                    val (func, args) = head() to tail()
                    if (func !is MalFunction) throw MalException("Expected function not found: $func.")
                    return func.apply(args)
                }
            }
        }
    }

    private fun evalAST(ast: MalType, env: Env): MalType =
        when (ast) {
            is MalSymbol -> env.get(ast)
            is MalList -> ast.elements.fold(MalList()) { list, elem -> list.apply { add(eval(elem, env)) } }
            else -> ast
        }

    fun print(expr: MalType) = Printer.printStr(expr)

    fun rep(string: String, env: Env) = print(eval(read(string), env))
}
