package com.mayurjajoo.screepto.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mayurjajoo.screepto.utils.Constants

@Database(
    entities = [CoinsData::class],
    version = Constants.DB_VERSION
)
abstract class CoinsDatabase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDAO

    companion object {
        @Volatile
        private var INSTANCE: CoinsDatabase? = null

        fun getDatabase(context: Context): CoinsDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        CoinsDatabase::class.java,
                        "coinsDB"
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}