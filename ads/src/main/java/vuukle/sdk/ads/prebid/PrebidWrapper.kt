package vuukle.sdk.ads.prebid

import android.content.Context
import org.prebid.mobile.*
import vuukle.sdk.ads.constants.SdkConstants

class PrebidWrapper {

    fun initializeHost(activityContext: Context) {
        // Get Ad from prebid
        Host.CUSTOM.hostUrl = SdkConstants.HOST_URL
        PrebidMobile.setPrebidServerHost(Host.CUSTOM)
        PrebidMobile.setPrebidServerAccountId(SdkConstants.PREBID_SERVER_ACCOUNT_ID)
        PrebidMobile.setPbsDebug(true)
        PrebidMobile.setShareGeoLocation(true)
        PrebidMobile.setApplicationContext(activityContext)
    }

    fun createBannerAdUnit(configId: String, width: Int, height: Int): BannerAdUnit {
        val bannerAdUnit = BannerAdUnit(configId, width, height)
        val parameters = BannerBaseAdUnit.Parameters()
        parameters.api = listOf(Signals.Api(6), Signals.Api(5))
        bannerAdUnit.parameters = parameters
        bannerAdUnit.setAutoRefreshPeriodMillis(120000)
        return bannerAdUnit
    }
}