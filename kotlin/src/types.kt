interface MalType

class MalConstant(val value: String) : MalType {
    override fun equals(other: Any?) = other is MalConstant && value == other.value
}

val NIL = MalConstant("nil")
val TRUE = MalConstant("true")
val FALSE = MalConstant("false")

class MalList(val elements: ArrayList<MalType>) : MalType {
    constructor() : this(ArrayList<MalType>())

    val head: MalType = elements.first()
    val tail: MalList = apply { elements.drop(1) }

    fun add(element: MalType) = elements.add(element)

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

    override fun equals(other: Any?) = other is MalNumber && value == other.value
}

class MalSymbol(val value: String) : MalType {
    override fun equals(other: Any?) = other is MalSymbol && value == other.value
}

class MalString(val value: String) : MalType {
    override fun equals(other: Any?) = other is MalString && value == other.value
}

class MalFunction(val lambda: (MalList) -> MalType) : MalType {

    fun apply(params: MalList) = lambda(params)
}

open class MalException(message: String) : Exception(message), MalType
class MalContinue : MalException("continue")
class MalReaderException(message: String) : MalException(message)
class MalPrinterException(message: String) : MalException(message)
