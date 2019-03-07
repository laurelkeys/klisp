object Step1 {

    fun main(args: Array<String>) {
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

