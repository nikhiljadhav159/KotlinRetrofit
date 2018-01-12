package com.example.kotlinretrofit.model

import java.io.Serializable

/**
 * Created by nikhil.jadhav on 30-11-2017.
 */
data class Photo(val id:String,
                 val likes:Int,
                 val favorites:Int,
                 val previewURL:String,
                 val webformatURL:String) :Serializable{
}