fun printStr(data: MalType): String = when (data) {
    is MalConstant -> data.value
    is MalList     -> data.elements.joinToString(" ", prefix = "(", postfix = ")") { printStr(it) }
    is MalNumber   -> data.value.toString()
    is MalSymbol   -> data.value
    is MalString   -> data.value
    else -> throw MalPrinterException("Unrecognized MalType: $data.")
}