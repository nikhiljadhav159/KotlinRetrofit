package com.example.kotlinretrofit.api

import com.example.kotlinretrofit.model.PhotoList
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by nikhil.jadhav on 30-11-2017.
 */
interface PhotoApi {
    //get the key from pixabay.com which is used for free photo api
    @GET("?key='Enter Your Key'&q=yellow&image_type=photo")
    fun retrivePhotos():Call<PhotoList>
}