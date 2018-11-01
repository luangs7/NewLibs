package com.example.retina.samples.main.retrofit

import com.example.retina.samples.main.model.Post
import com.example.retina.samples.main.model.User
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("user")
    fun getUser():Call<List<User>>


    @GET("posts")
    fun getPost():Call<List<Post>>

    @GET("posts")
    fun getPostDeferred():Deferred<Response<List<Post>>>

    @GET("posts/{id}")
    fun getPostId(@Path("id") id:Int):Call<Post>


}