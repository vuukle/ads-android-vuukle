package vuukle.sdk.ads.model

import vuukle.sdk.ads.ext.toAdSize

data class VuukleAdSize(
    val name: Type
) {
    private val isFluid = name == Type.FLUID
    private var width: Int? = null
    private var height: Int? = null

    /**
     * todo
     */
    fun addFluidSize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    /**
     * todo
     */
    fun width(): Int {
        return when {
            isFluid -> width!!
            else -> name.toAdSize().width
        }
    }

    /**
     * todo
     */
    fun height(): Int {
        return when {
            isFluid -> height!!
            else -> name.toAdSize().height
        }
    }

    enum class Type {
        INVALID, BANNER, MEDIUM_RECTANGLE, LARGE_BANNER, FULL_BANNER, FLUID
    }
}