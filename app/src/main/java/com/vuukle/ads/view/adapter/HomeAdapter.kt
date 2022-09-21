package com.vuukle.ads.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vuukle.ads.R
import com.vuukle.ads.model.HomeModel
import vuukle.sdk.ads.model.VuukleAdSize

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var dataList = ArrayList<HomeModel>()

    private var onItemClickListener: ((HomeModel) -> Unit)? = null

    fun addOnItemClickListener(onItemClickListener: (HomeModel) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    fun submitData() {
        dataList = arrayListOf(
            HomeModel("MEDIUM RECTANGLE", VuukleAdSize.Type.MEDIUM_RECTANGLE),
            HomeModel("LARGE BANNER", VuukleAdSize.Type.LARGE_BANNER),
            HomeModel("FULL BANNER", VuukleAdSize.Type.FULL_BANNER),
            HomeModel("FLUID", VuukleAdSize.Type.FLUID),
            HomeModel("BANNER", VuukleAdSize.Type.BANNER),
            HomeModel("VUUKLE ADS ACTIVITY", VuukleAdSize.Type.INVALID),
        )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(dataList[position], onItemClickListener, position)
    }

    override fun getItemCount(): Int = dataList.size

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)

        fun onBind(
            homeModel: HomeModel,
            onItemClickListener: ((HomeModel) -> Unit)?,
            position: Int
        ) {
            title.text = homeModel.title

            itemView.setOnClickListener {
                onItemClickListener?.invoke(homeModel)
            }
        }

    }
}