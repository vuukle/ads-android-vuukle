package vuukle.sdk.ads.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdView
import vuukle.sdk.ads.ext.toAdSize
import vuukle.sdk.ads.model.VuukleAdSize

class VuukleAdView(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {

    private var googleAdView: AdView
    private lateinit var vuukleAdSize: VuukleAdSize

    fun getAdView(): ViewGroup = googleAdView
    fun getVuukleAdSize() = vuukleAdSize

    init {
        googleAdView = AdView(context)
        val lp = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        googleAdView.layoutParams = lp
        this.addView(googleAdView)
    }

    fun setAdSize(adSize: VuukleAdSize.Type) {
        vuukleAdSize = when (adSize) {
            VuukleAdSize.Type.BANNER -> {
                VuukleAdSize(
                    name = VuukleAdSize.Type.BANNER
                )
            }
            else -> {
                VuukleAdSize(
                    name = VuukleAdSize.Type.INVALID
                )
            }
        }

        googleAdView.setAdSize(vuukleAdSize.name.toAdSize())
    }

    fun setAdUnitId(adUnitId: String) {
        googleAdView.adUnitId = adUnitId
    }
}