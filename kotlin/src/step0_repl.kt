fun main(args: Array<String>) {
    while (true) {
        val input = readLine() ?: break // break if null (EOF)
        println("user> ${rep(input)}")
    }
}

fun read(string: String) = string

fun eval(string: String) = string

fun print(string: String) = string

fun rep(string: String) = print(eval(read(string)))