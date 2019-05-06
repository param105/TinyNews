package com.param.tinynewsapp.model

import android.os.Parcel
import android.os.Parcelable

class NewsModelRecycler() : Parcelable {

    var name: String? = null
    var country: String? = null
    var city: String? = null
    var imgURL: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        country = parcel.readString()
        city = parcel.readString()
        imgURL = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeString(city)
        parcel.writeString(imgURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsModelRecycler> {
        override fun createFromParcel(parcel: Parcel): NewsModelRecycler {
            return NewsModelRecycler(parcel)
        }

        override fun newArray(size: Int): Array<NewsModelRecycler?> {
            return arrayOfNulls(size)
        }
    }
}