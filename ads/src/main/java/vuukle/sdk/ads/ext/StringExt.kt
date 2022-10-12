package vuukle.sdk.ads.ext

fun List<String>.toLogIds(): List<String> {
    val result = arrayListOf<String>()
    this.forEach {
        if (it.isNotBlank()) {
            result.add(it.substring(it.lastIndexOf(":") + 1))
        }
    }
    return result
}

fun List<String>.getIndexByLogId(id: String): Int? {
    this.forEachIndexed { index, log ->
        if (log.contains(id)) {
            return index
        }
    }
    return null
}