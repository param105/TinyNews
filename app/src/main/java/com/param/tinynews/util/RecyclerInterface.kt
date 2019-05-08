package com.param.tinynews.util

import retrofit2.Call
import retrofit2.http.GET

interface RecyclerInterface {

    @get:GET("json_parsing.php")
    val string: Call<String>

    companion object {

       // val JSONURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/"
        val JSONURL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=de63571ebc714ae6828e37c65bb712bf"
    }
}