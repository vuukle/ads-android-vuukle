package vuukle.sdk.ads.model

import vuukle.sdk.ads.ext.toAdSize

data class VuukleAdSize(
    val name: Type,
    val width: Int? = null,
    val height: Int? = null
) {

    fun width(): Int = name.toAdSize(width = width, height = height).width

    fun height(): Int = name.toAdSize(width = width, height = height).height

    enum class Type {
        INVALID, BANNER, MEDIUM_RECTANGLE, LARGE_BANNER, FULL_BANNER, FLUID
    }
}