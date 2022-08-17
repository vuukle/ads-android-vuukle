package vuukle.sdk.ads.exception

open class VuukleAdsException(message: String = "") : Throwable(message)
class VuukleAdsConfigurationNotFound(message: String = "") : VuukleAdsException(message)
class VuukleAdsHostNotFound(message: String = "") : VuukleAdsException(message)
class VuukleAdLoadFail(message: String = "") : VuukleAdsException(message)