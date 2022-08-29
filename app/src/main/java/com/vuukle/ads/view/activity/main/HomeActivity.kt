package com.vuukle.ads

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vuukle.ads.view.activity.*
import com.vuukle.ads.view.adapter.HomeAdapter
import vuukle.sdk.ads.model.VuukleAdSize

class HomeActivity : AppCompatActivity() {

    private val homeRecycler: RecyclerView by lazy { findViewById(R.id.homeRecycler) }

    private val homeAdapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
    }

    private fun initAdapter() {
        homeRecycler.apply {
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }
        homeAdapter.addOnItemClickListener {
            handleItemClick(it.type)
        }
        homeAdapter.submitData()
    }

    private fun handleItemClick(type: VuukleAdSize.Type) {
        when (type) {
            VuukleAdSize.Type.MEDIUM_RECTANGLE -> {
                startActivity(Intent(this, MediumRectangleActivity::class.java))
            }
            VuukleAdSize.Type.BANNER -> {
                startActivity(Intent(this, BannerActivity::class.java))
            }
            VuukleAdSize.Type.LARGE_BANNER -> {
                startActivity(Intent(this, LargeBannerActivity::class.java))
            }
            VuukleAdSize.Type.FULL_BANNER -> {
                startActivity(Intent(this, FullBannerActivity::class.java))
            }
            VuukleAdSize.Type.FLUID -> {
                startActivity(Intent(this, FluidActivity::class.java))
            }
            else -> Unit
        }
    }
}