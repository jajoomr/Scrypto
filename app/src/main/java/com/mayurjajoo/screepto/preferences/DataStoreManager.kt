package com.mayurjajoo.screepto.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mayurjajoo.screepto.utils.Constants
import kotlinx.coroutines.flow.first

/**
 * Manages saving and fetching data from data store preferences
 */
class DataStoreManager(private val context: Context) {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = Constants.PREFERENCE_DATA_STORE_NAME
        )
        val TIME = stringPreferencesKey(Constants.LAST_SYNC_TIME_KEY)
    }

    /**
     * Saves data to data store
     */
    suspend fun saveToDataStore(time: String) {
        context.dataStore.edit {
            it[TIME] = time
        }
    }

    /**
     * Get data from data store
     */
    suspend fun getFromDataStore(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey].toString()
    }
}