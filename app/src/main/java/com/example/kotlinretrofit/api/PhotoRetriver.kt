package com.example.kotlinretrofit.api

import com.example.kotlinretrofit.model.PhotoList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by nikhil.jadhav on 30-11-2017.
 */
class PhotoRetriver {
    private lateinit var service : PhotoApi

    init {

        //region This is used for logging the Retrofit request and response.
        val okHttpLog: HttpLoggingInterceptor = HttpLoggingInterceptor()
        okHttpLog.setLevel(HttpLoggingInterceptor.Level.BODY)
        //endregion

        //region Setting up the okHttpClient for retrofit
        val okHttp:OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(okHttpLog)
                .build()
        //endregion

        //region Settin up the retrofit request
        val retrofit:Retrofit = Retrofit.Builder()
                .baseUrl("https://pixabay.com/api/")
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        //endregion

        //region creating a retrofit service request for PhotoApi class
        service = retrofit.create(PhotoApi::class.java)
        //endregion
    }

    fun getPhotos(callback: Callback<PhotoList>){
        //region creating call for service request and handle the call back from request
        val call = service.retrivePhotos()
        call.enqueue(callback)
        //endregion
    }
}