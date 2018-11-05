package com.example.sampleapp.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.retina.samples.main.model.Post
import com.example.retina.samples.main.retrofit.ApiInterface
import com.example.sampleapp.data.Resource
import kotlinx.coroutines.coroutineScope


class PostRepository(val api:ApiInterface) {

    private val _result = MutableLiveData<Resource<List<Post>>>()

    val result
            get() = _result as LiveData<Resource<List<Post>>>



    suspend fun getPosts(numberOfItens: Int) = coroutineScope {
        _result.value = Resource.loading(null)

        val response = api.getPost(numberOfItens).await()
        if(response.isSuccessful){
            _result.postValue(Resource.success(response.body()))
        }else{
            //TODO: Handler error here
            _result.postValue(Resource.error("Error",null))
        }
    }

    suspend fun getPostsNow(numberOfItens: Int) : Resource<List<Post>> {
        val response = api.getPost(numberOfItens).await()
        if(response.isSuccessful){
            return Resource.success(response.body())
        }else{
            //TODO: Handler error here
            return Resource.error(response.errorBody()!!.string(),null)
        }
    }


}