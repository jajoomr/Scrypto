package com.mayurjajoo.screepto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mayurjajoo.screepto.R
import com.mayurjajoo.screepto.databinding.ActivityCoinDetailBinding
import com.mayurjajoo.screepto.db.CoinsData
import com.mayurjajoo.screepto.utils.Constants

/**
 * Class to display details of the coin item clicked.
 * It is responsible for displaying the details of coin parceled via intent
 */
class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }


    private fun updateUI() {
        val coinsEntity = intent.getParcelableExtra<CoinsData>(Constants.COINS_ENTITY_PARCEL_KEY)
        binding.tvCoinName.text = coinsEntity?.name

        binding.tvOneDayReturn.text = coinsEntity?.percentChange24h.formattedValue()
        binding.tvOneWeekReturn.text = coinsEntity?.percentChange7d.formattedValue()
        binding.tvOneMonthReturn.text = coinsEntity?.percentChange30d.formattedValue()
        binding.tvThreeMonthReturn.text = coinsEntity?.percentChange90d.formattedValue()

        if (coinsEntity!!.percentChange24h >= 0) {
            binding.oneDayStatusIcon.setImageResource(R.drawable.up_30)
        } else {
            binding.oneDayStatusIcon.setImageResource(R.drawable.down_30)
        }

        if (coinsEntity.percentChange7d >= 0) {
            binding.oneWeekStatusIcon.setImageResource(R.drawable.up_30)
        } else {
            binding.oneWeekStatusIcon.setImageResource(R.drawable.down_30)
        }

        if (coinsEntity.percentChange30d >= 0) {
            binding.oneMonthStatusIcon.setImageResource(R.drawable.up_30)
        } else {
            binding.oneMonthStatusIcon.setImageResource(R.drawable.down_30)
        }

        if (coinsEntity.percentChange30d >= 0) {
            binding.threeMonthStatusIcon.setImageResource(R.drawable.up_30)
        } else {
            binding.threeMonthStatusIcon.setImageResource(R.drawable.down_30)
        }
    }
}

/**
 * Formats double value to 2 decimal places and adds percentage suffix
 */
private fun Double?.formattedValue(): String {
    return String.format("%.2f", this) + " %"
}
