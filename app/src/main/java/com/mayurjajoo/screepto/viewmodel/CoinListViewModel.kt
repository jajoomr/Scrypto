package com.mayurjajoo.screepto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayurjajoo.screepto.db.CoinsData
import com.mayurjajoo.screepto.repository.CoinsRepository
import com.mayurjajoo.screepto.repository.Response
import com.mayurjajoo.screepto.utils.Constants
import kotlinx.coroutines.launch

/**
 * Responsible for getting data from repository and sending it to lifecycle owner
 */
class CoinListViewModel(private val repository: CoinsRepository) : ViewModel() {
    val coinsLiveData: LiveData<Response<List<CoinsData>>>
        get() = repository.coinsList

    val lastSyncedTime: LiveData<String>
        get() = repository.lastSyncTime

    init {
        getCoinsData()
    }

    /**
     * Get Coins' data from repository
     */
    fun getCoinsData() {
        viewModelScope.launch {
            repository.getCoins(Constants.PAGE_START, Constants.PAGE_END)
        }
    }
}