package com.example.kotlinretrofit.activities

import android.app.ProgressDialog
import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.example.kotlinretrofit.R
import com.example.kotlinretrofit.adapters.RvAdapter
import com.example.kotlinretrofit.api.PhotoRetriver
import com.example.kotlinretrofit.model.Photo
import com.example.kotlinretrofit.model.PhotoList
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var photoList:List<Photo>
    lateinit var rvAdapter:RvAdapter
    var TAG:String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialise()

        makeApiCall()

    }

    private fun makeApiCall() {

        //region creating a callback for response
        val callback = object : Callback<PhotoList>{

            override fun onResponse(call: Call<PhotoList>?, response: Response<PhotoList>?) {
                this@MainActivity.photoList = response?.body()?.hits!!
                rvAdapter = RvAdapter(photoList,this@MainActivity)
                rvMain.adapter = rvAdapter
            }

            override fun onFailure(call: Call<PhotoList>?, t: Throwable?) {
                Log.e(TAG,t.toString())
                Toast.makeText(this@MainActivity,"Error ${t.toString()}",Toast.LENGTH_SHORT).show()
            }
        }
        //endregion


        //region create request call and handle the callback
        val retriver:PhotoRetriver = PhotoRetriver()
        retriver.getPhotos(callback)
        //endregion

    }

    private fun initialise() {
        setSupportActionBar(tbMain as Toolbar)
        supportActionBar?.title = "Retrofit Call"

        val llm:LinearLayoutManager = LinearLayoutManager(this)
        llm.orientation = LinearLayout.VERTICAL

        rvMain?.setHasFixedSize(true)
        rvMain?.layoutManager = llm
    }

    override fun onClick(view: View?) {
        //Handle the onclick events from the recyclerView
        //get the clicked item postion by calling getPhoto from adapter
        val holder = view?.tag as RvAdapter.PhotoViewHolder
        var photo= rvAdapter.getPhoto(holder.adapterPosition)
        Toast.makeText(this@MainActivity,"Clicked position ${photo.likes}",Toast.LENGTH_SHORT).show()
    }
}
