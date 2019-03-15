package main

class Reader(private val tokens: Sequence<String>) {

    private var position = 0

    fun next() = tokens.elementAtOrNull(position++)
    fun peek() = tokens.elementAtOrNull(position)

    companion object {

        private val TOKENS =
            Regex(pattern = "[\\s,]*(~@|[\\[\\]{}()'`~^@]|\"(?:\\\\.|[^\\\\\"])*\"?|;.*|[^\\s\\[\\]{}('\"`,;)]*)")

        private val ATOMS =
            Regex(pattern = "(^-?[0-9]+$)|(^nil$)|(^true$)|(^false$)|^\"(.*)\"$|(^[^\"]*$)")

        private const val ATOMS_GROUP_NUMBER = 1 // (^-?[0-9]+$)
        private const val ATOMS_GROUP_NIL    = 2 // (^nil$)
        private const val ATOMS_GROUP_TRUE   = 3 // (^true$)
        private const val ATOMS_GROUP_FALSE  = 4 // (^false$)
        private const val ATOMS_GROUP_STRING = 5 // ^\"(.*)\"$
        private const val ATOMS_GROUP_SYMBOL = 6 // (^[^\"]*$)

        fun readStr(string: String) = readForm(Reader(tokenize(string)))

        private fun tokenize(string: String): Sequence<String> = TOKENS
            .findAll(string)
            .map { it.groups[1]?.value as String }
            .filter { it.isNotBlank() }

        private fun readForm(reader: Reader): MalType = when (reader.peek()) {
            null -> throw MalContinue()
            "("  -> readList(reader)
            else -> readAtom(reader)
        }

        private fun readList(reader: Reader): MalType = MalList().also {
            if (reader.next() != "(") throw MalReaderException("Expected '(' not found.")

            while (reader.peek() != ")") {
                if (reader.peek() == null) throw MalReaderException("Expected ')' not found.") // EOF
                it.add(readForm(reader))
            }

            reader.next()
        }

        private fun readAtom(reader: Reader): MalType = reader.next()?.let { next ->
            ATOMS.find(next)?.groups?.let { groups ->
                when {
                    groups[ATOMS_GROUP_NUMBER]?.value != null -> MalNumber(next.toInt())
                    groups[ATOMS_GROUP_NIL]?.value    != null -> NIL
                    groups[ATOMS_GROUP_TRUE]?.value   != null -> TRUE
                    groups[ATOMS_GROUP_FALSE]?.value  != null -> FALSE
                    groups[ATOMS_GROUP_STRING]?.value != null -> MalString(next)
                    groups[ATOMS_GROUP_SYMBOL]?.value != null -> MalSymbol(next)
                    else -> throw MalReaderException("Unexpected atom: $next.")
                }
            } ?: throw MalReaderException("Unexpected atom: $next.")
        } ?: throw MalReaderException("Unexpected atom.")
    }
}