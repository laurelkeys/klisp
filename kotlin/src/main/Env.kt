package main

class Env(private val outer: Env?) : MalType {

    private val data = HashMap<MalSymbol, MalType>()

    fun get(key: MalSymbol): MalType {
        return find(key) ?: throw MalException("Key not found in environment: ${key.value}.")
    }

    fun set(key: MalSymbol, value: MalType): MalType {
        data.set(key, value)
        return value
    }

    private fun find(key: MalSymbol): MalType? {
        return data.get(key) ?: outer?.find(key)
    }
}