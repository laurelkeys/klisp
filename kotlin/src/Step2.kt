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
            else -> evalAST(ast, env).let { list ->
                when (list) {
                    !is MalList -> throw MalException("Expected list not found: $list.")
                    else -> {
                        list.head()?.let { func ->
                            when (func) {
                                !is MalFunction -> throw MalException("Expected function not found: $func.")
                                else -> func.apply(list.tail())
                            }
                        } ?: throw MalException("Unexpected empty list: $list.")
                    }
                }
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