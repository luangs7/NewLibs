package com.example.retina.samples.main.retrofit

import com.example.retina.samples.main.model.Post
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("getPost.php")
    fun getPost(@Query("posts") numberOfItens:Int):Deferred<Response<List<Post>>>

}