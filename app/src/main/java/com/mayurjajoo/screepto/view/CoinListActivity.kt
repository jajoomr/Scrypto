package com.mayurjajoo.screepto.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mayurjajoo.screepto.R
import com.mayurjajoo.screepto.ScryptoApplication
import com.mayurjajoo.screepto.adapter.CoinsDataAdapter
import com.mayurjajoo.screepto.databinding.ActivityCoinListBinding
import com.mayurjajoo.screepto.db.CoinsData
import com.mayurjajoo.screepto.repository.Response
import com.mayurjajoo.screepto.utils.Constants
import com.mayurjajoo.screepto.viewmodel.CoinListViewModel
import com.mayurjajoo.screepto.viewmodel.CoinListViewModelFactory

/**
 * This activity is responsible for displaying the list of crypto coins.
 */
class CoinListActivity : AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mCoinListViewModel:CoinListViewModel
    private lateinit var binding: ActivityCoinListBinding
    private lateinit var mCoinsDataList: MutableList<CoinsData>
    private lateinit var mCoinsDataAdapter: CoinsDataAdapter
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialise()
    }

    /**
     * initialises components
     */
    private fun initialise() {
        mCoinListViewModel = ViewModelProvider(
            this,
            CoinListViewModelFactory(ScryptoApplication.repository)
        ).get(CoinListViewModel::class.java)

        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        )

        mCoinsDataList = ArrayList()
        mCoinsDataAdapter = CoinsDataAdapter(this, mCoinsDataList)
        mCoinsDataAdapter.onItemClickListener = {
            navigateToDetailActivity(it)
        }
        binding.recyclerView.apply {
            adapter = mCoinsDataAdapter
            layoutManager = LinearLayoutManager(this@CoinListActivity)
        }
    }

    /**
     * Fires intent to navigate to CoinDetailActivity and parcels Coins data
     */
    private fun navigateToDetailActivity(coinsData: CoinsData) {
        val intent = Intent(this, CoinDetailActivity::class.java)
        intent.putExtra(Constants.COINS_ENTITY_PARCEL_KEY, coinsData)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        observeLiveDataAndUpdateUi()
    }

    /**
     * Observes data from view model and updates UI
     */
    private fun observeLiveDataAndUpdateUi() {
        mCoinListViewModel.coinsLiveData.observe(this, {
            when (it) {
                is Response.Loading -> {
                    //currently do nothing
                }
                is Response.Success -> {
                    mCoinsDataList.clear()
                    it.data?.let { it1 -> mCoinsDataList.addAll(it1) }
                    mCoinsDataAdapter.notifyDataSetChanged()
                }

                is Response.Failure -> {
                    Log.d(TAG, it.errorMessage.toString())
                    Toast.makeText(this, getString(R.string.error_message),
                        Toast.LENGTH_SHORT).show()
                }
            }
        })

        mCoinListViewModel.lastSyncedTime.observe(this, {
            binding.tvLastSyncedTime.text = it })
    }

    override fun onRefresh() {
        mCoinListViewModel.getCoinsData()
        binding.swipeRefreshLayout.isRefreshing = false
    }
}