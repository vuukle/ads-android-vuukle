package vuukle.sdk.ads.model

import vuukle.sdk.ads.ext.toAdSize

data class VuukleAdSize(
    val name: Type
) {

    fun width(): Int = name.toAdSize().width
    fun height(): Int = name.toAdSize().height

    enum class Type {
        INVALID, BANNER
    }
}