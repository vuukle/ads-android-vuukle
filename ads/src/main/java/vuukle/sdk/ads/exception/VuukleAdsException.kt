package vuukle.sdk.ads.exception

open class VuukleAdsException(message: String? = null) : Throwable(message)
class VuukleAdsInitializationException(message: String? = null) : VuukleAdsException(message)
class VuukleLoadAdError(message: String? = null) : VuukleAdsException(message)
class VuukleAdsConfigurationNotFound(message: String? = null) : VuukleAdsException(message)
class VuukleAdsHostNotFound(message: String? = null) : VuukleAdsException(message)
class VuukleAdLoadFail(message: String? = null) : VuukleAdsException(message)