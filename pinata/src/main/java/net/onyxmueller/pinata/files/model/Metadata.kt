package net.onyxmueller.pinata.files.model

//class Metadata<K, V>: HashMap<K, V>() {
//    override fun toString(): String {
//        return buildString {
//            entries.forEachIndexed { index, (key, value) ->
//                append("metadata[")
//                append(key)
//                append("]=")
//                append(value)
//                if (index < entries.size - 1) {
//                    append("&")
//                }
//            }
//        }
//    }
//}

data class Metadata(val params: MutableMap<String, Any> = mutableMapOf()) {
    override fun toString(): String = params.map {
        val value = it.value
        if (value !is Iterable<*>) {
            "metadata[" + it.key + "]" + "=" + it.value.toString()
        } else {
            createPairs(it.key, value)
        }
    }.joinToString("&")

    fun createPairs(key: String, value: Iterable<*>): String {
        return value.mapIndexed { idx, value ->
            val useKey = key + "[" + idx + "]"
            if (value !is Iterable<*>) {
                useKey + "=" + (value?.toString() ?: "")
            } else
                createPairs(useKey, value)

        }.joinToString("&")
    }
}
