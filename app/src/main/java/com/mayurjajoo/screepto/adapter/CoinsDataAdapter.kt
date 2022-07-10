package com.mayurjajoo.screepto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mayurjajoo.screepto.R
import com.mayurjajoo.screepto.db.CoinsData
import com.mayurjajoo.screepto.utils.Constants

class CoinsDataAdapter(
    private val context: Context,
    private val coinsDataList: List<CoinsData>
) :
    RecyclerView.Adapter<CoinsDataAdapter.CoinsViewHolder>() {

    var onItemClickListener: ((CoinsData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.coin_item_layout, parent, false)
        return CoinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        val coinsData: CoinsData = coinsDataList[position]
        holder.coinName.text = coinsData.name

        holder.coinValue.text = String.format("$ %.2f", coinsData.price)

        val oneDayChange: String =
            String.format("%.2f", coinsData.percentChange24h) + Constants.PERCENTAGE_SUFFIX
        holder.oneDayChange.text = oneDayChange

        if (coinsData!!.percentChange24h >= 0) {
            holder.oneDayStatusImg.setImageResource(R.drawable.up_30)
        } else {
            holder.oneDayStatusImg.setImageResource(R.drawable.down_30)
        }

        val imageUrl = Constants.BASE_IMAGE_URL + coinsData.id + Constants.IMAGE_URL_SUFFIX
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.blockchain)
            .error(R.drawable.blockchain)
            .into(holder.coinImage)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(coinsData)
        }
    }

    override fun getItemCount(): Int {
        return coinsDataList.size
    }

    class CoinsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coinName: TextView = itemView.findViewById(R.id.tv_coin_name)
        val coinImage: ImageView = itemView.findViewById(R.id.img_coin)
        val coinValue: TextView = itemView.findViewById(R.id.tv_coin_value)
        val oneDayChange: TextView = itemView.findViewById(R.id.tv_one_day_change)
        val oneDayStatusImg: ImageView = itemView.findViewById(R.id.img_one_day_status)
    }
}