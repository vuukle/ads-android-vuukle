package vuukle.sdk.ads.model

import vuukle.sdk.ads.ext.toAdSize

data class VuukleAdSize(
    val name: Type
) {

    fun width(): Int {
        val adSize = name.toAdSize()
       return if (adSize.isFluid.not()){
            adSize.width
        } else{
            //TODO should get size from parent view
            300
       }
    }
    fun height(): Int {
        val adSize = name.toAdSize()
        return if (adSize.isFluid.not()){
            adSize.height
        } else{
            //TODO should get size from parent view
            300
        }
    }

    enum class Type {
        INVALID, BANNER, MEDIUM_RECTANGLE, LARGE_BANNER, FULL_BANNER, FLUID
    }
}