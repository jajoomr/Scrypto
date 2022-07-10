package com.mayurjajoo.screepto

import android.app.Application
import com.mayurjajoo.screepto.api.CoinsService
import com.mayurjajoo.screepto.api.RetrofitHelper
import com.mayurjajoo.screepto.db.CoinsDatabase
import com.mayurjajoo.screepto.repository.CoinsRepository

/**
 * Application class
 */
class ScryptoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initialise()
    }

    private fun initialise() {
        val coinsService = RetrofitHelper.getInstance().create(CoinsService::class.java)
        val coinsDB = CoinsDatabase.getDatabase(applicationContext)
        repository = CoinsRepository(coinsService, applicationContext, coinsDB)
    }

    companion object {
        lateinit var repository: CoinsRepository
    }
}