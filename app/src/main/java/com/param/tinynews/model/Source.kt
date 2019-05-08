package com.param.tinynews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Source {

    @SerializedName("id")
    @Expose
    var id: Any? = null
    @SerializedName("author")
    @Expose
    var name: String? = null

}
