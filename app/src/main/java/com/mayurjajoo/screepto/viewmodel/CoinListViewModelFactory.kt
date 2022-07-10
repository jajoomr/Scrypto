package com.mayurjajoo.screepto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mayurjajoo.screepto.repository.CoinsRepository

/**
 * Creates and provides View Model instance to requested activity
 */
class CoinListViewModelFactory(private val repository: CoinsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinListViewModel(repository) as T
    }
}