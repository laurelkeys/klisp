object ReadEvalPrint {
    private fun read(string: String) = readStr(string)
    private fun eval(string: MalType) = string
    private fun print(string: MalType) = printStr(string)
    fun rep(string: String) = print(eval(read(string)))
}

fun main(args: Array<String>) {
    while (true) {
        val input = readLine() ?: break // break if null (EOF)
        println("user> ${ReadEvalPrint.rep(input)}")
    }
}
