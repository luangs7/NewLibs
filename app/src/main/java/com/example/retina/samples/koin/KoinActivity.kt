package com.example.retina.samples.koin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retina.samples.R
import com.example.retina.samples.main.model.Post
import com.example.retina.samples.main.retrofit.ApiInterface
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KoinActivity : AppCompatActivity() {

    val api : ApiInterface by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        api.getPost().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.i("lista",response.body()?.joinToString(","))
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.i("error",this@KoinActivity.localClassName)
            }
        })


    }
}
