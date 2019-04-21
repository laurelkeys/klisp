package klisp

interface MalType

class MalConstant(val value: String) : MalType {

    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?) = other is MalConstant && value == other.value
}

val NIL = MalConstant("nil")
val TRUE = MalConstant("true")
val FALSE = MalConstant("false")

class MalList(val elements: ArrayList<MalType>) : MalType {
    constructor() : this(ArrayList<MalType>())

    fun head(): MalType = elements.first()
    fun tail(): MalList = MalList(ArrayList(elements.drop(1)))

    fun add(element: MalType) {
        elements.add(element)
    }

    fun isSpecialForm(value: String) = head().let { head ->
        head is MalSymbol && head.value == value && value in SpecialForm.values().map { it.value }
    }

    enum class SpecialForm(val value: String) {
        DEF("def!"),
        LET("let*")
    }

    override fun hashCode() = elements.hashCode()
    override fun equals(other: Any?) = other is MalList
            && elements.size == other.elements.size
            && elements.zip(other.elements).all { it.first == it.second }
}

class MalNumber(val value: Int) : MalType {

    operator fun plus(other: MalNumber) = MalNumber(value + other.value)
    operator fun minus(other: MalNumber) = MalNumber(value - other.value)
    operator fun times(other: MalNumber) = MalNumber(value * other.value)
    operator fun div(other: MalNumber) = MalNumber(value / other.value)
    operator fun compareTo(other: MalNumber) = value.compareTo(other.value)

    override fun hashCode() = value
    override fun equals(other: Any?) = other is MalNumber && value == other.value
}

class MalSymbol(val value: String) : MalType {

    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?) = other is MalSymbol && value == other.value
}

class MalString(val value: String) : MalType {

    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?) = other is MalString && value == other.value
}

class MalFunction(private val lambda: (MalList) -> MalType) : MalType {

    fun apply(params: MalList): MalType = lambda.invoke(params)
}

open class MalException(message: String) : Exception(message), MalType
class MalContinue : MalException("continue")
class MalReaderException(message: String) : MalException(message)
class MalPrinterException(message: String) : MalException(message)
