package com.mayurjajoo.screepto.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinsData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    val percentChange1h: Double,
    val percentChange24h: Double,
    val percentChange30d: Double,
    val percentChange60d: Double,
    val percentChange7d: Double,
    val percentChange90d: Double,
    val price: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeDouble(percentChange1h)
        parcel.writeDouble(percentChange24h)
        parcel.writeDouble(percentChange30d)
        parcel.writeDouble(percentChange60d)
        parcel.writeDouble(percentChange7d)
        parcel.writeDouble(percentChange90d)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoinsData> {
        override fun createFromParcel(parcel: Parcel): CoinsData {
            return CoinsData(parcel)
        }

        override fun newArray(size: Int): Array<CoinsData?> {
            return arrayOfNulls(size)
        }
    }
}