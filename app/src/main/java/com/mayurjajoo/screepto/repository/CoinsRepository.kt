package com.mayurjajoo.screepto.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mayurjajoo.screepto.api.CoinsService
import com.mayurjajoo.screepto.db.CoinsDatabase
import com.mayurjajoo.screepto.db.CoinsData
import com.mayurjajoo.screepto.model.CryptoCurrency
import com.mayurjajoo.screepto.preferences.DataStoreManager
import com.mayurjajoo.screepto.utils.Constants
import com.mayurjajoo.screepto.utils.NetworkUtils
import java.lang.Exception
import java.util.*

/**
 * Responsible for fetching data from api, db and preferences.
 * Responsible for saving data to db and preferences.
 */
class CoinsRepository(
    private val coinsService: CoinsService,
    private val applicationContext: Context,
    private val coinsDataBase: CoinsDatabase
) {
    private val mCoinsListLiveData = MutableLiveData<Response<List<CoinsData>>>()
    val coinsList: LiveData<Response<List<CoinsData>>>
        get() = mCoinsListLiveData

    private val mLastSyncTimeLiveData: MutableLiveData<String> = MutableLiveData<String>()
    val lastSyncTime: LiveData<String>
        get() = mLastSyncTimeLiveData

    private lateinit var dataStoreManager: DataStoreManager

    /**
     * Makes an api call and stores data into data base.
     */
    suspend fun getCoins(start: Int, end: Int) {
        dataStoreManager = DataStoreManager(applicationContext)

        if (NetworkUtils.isInternetAvailable((applicationContext))) {
            try {
                val result = coinsService.getCoinsData(start, end)
                if (result?.body() != null) {
                    //save to data base
                    coinsDataBase.coinsDao()
                        .insertCoins(createList(result.body()!!.data.cryptoCurrencyList))
                    //save instance of time in DB
                    dataStoreManager.saveToDataStore(getSystemTime())
                }
            } catch (e: Exception) {
                // report an error if network call fails or saving to db fails
                mCoinsListLiveData.postValue(Response.Failure(e.toString()))
            }
        }
        try {
            //try fetching data from database and update list
            mCoinsListLiveData.postValue(Response.Success(coinsDataBase.coinsDao().getCoinsData()))
        } catch (e: Exception) {
            //return error message if there is any error fetching data from DB
            mCoinsListLiveData.postValue(Response.Failure(e.toString()))
        }
        mLastSyncTimeLiveData.value =
            dataStoreManager.getFromDataStore(Constants.LAST_SYNC_TIME_KEY)
    }

    private fun getSystemTime(): String {
        val time = Calendar.getInstance().time
        return time.toString()
    }

    private fun createList(cryptoCoinList: List<CryptoCurrency>): List<CoinsData> {
        val list: MutableList<CoinsData> = mutableListOf()
        cryptoCoinList.forEach {
            val coinsData = CoinsData(
                id = it.id,
                name = it.name,
                percentChange1h = it.quotes[0].percentChange1h,
                percentChange24h = it.quotes[0].percentChange24h,
                percentChange30d = it.quotes[0].percentChange30d,
                percentChange60d = it.quotes[0].percentChange60d,
                percentChange7d = it.quotes[0].percentChange7d,
                percentChange90d = it.quotes[0].percentChange90d,
                price = it.quotes[0].price
            )
            list.add(coinsData)
        }
        return list
    }
}
