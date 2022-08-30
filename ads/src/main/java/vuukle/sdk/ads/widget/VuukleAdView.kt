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

    fun setAdSize(
        adSize: VuukleAdSize.Type,
        width: Int? = null,
        height: Int? = null
    ) {
        vuukleAdSize = when (adSize) {
            VuukleAdSize.Type.BANNER -> {
                VuukleAdSize(
                    name = VuukleAdSize.Type.BANNER
                )
            }
            VuukleAdSize.Type.MEDIUM_RECTANGLE -> {
                VuukleAdSize(
                    name = VuukleAdSize.Type.MEDIUM_RECTANGLE
                )
            }
            VuukleAdSize.Type.FULL_BANNER -> {
                VuukleAdSize(
                    name = VuukleAdSize.Type.FULL_BANNER
                )
            }
            VuukleAdSize.Type.FLUID -> {
                VuukleAdSize(
                    name = VuukleAdSize.Type.FLUID,
                    width = width,
                    height = height
                )
            }
            VuukleAdSize.Type.LARGE_BANNER -> {
                VuukleAdSize(
                    name = VuukleAdSize.Type.LARGE_BANNER
                )
            }
            else -> {
                VuukleAdSize(
                    name = VuukleAdSize.Type.INVALID
                )
            }
        }
        googleAdView.setAdSize(vuukleAdSize.name.toAdSize(width, height))
    }

    fun setAdUnitId(adUnitId: String) {
        googleAdView.adUnitId = adUnitId
    }
}