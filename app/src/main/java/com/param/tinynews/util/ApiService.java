package com.param.tinynews.util;

import com.param.tinynews.model.NewsList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of EmployeeList
    */
    @GET("/v2/top-headlines?country=in&apiKey=de63571ebc714ae6828e37c65bb712bf")
    Call<NewsList> getNewsListJSON();
}
