package com.param.tinynewsapp.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    /********
     * URLS
     *******/
    private static final String ROOT_URL = "https://newsapi.org";
    private static final String Url_extra = "/v2/top-headlines?country=in&apiKey=de63571ebc714ae6828e37c65bb712bf";
    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
