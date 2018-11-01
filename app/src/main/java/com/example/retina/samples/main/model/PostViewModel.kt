package com.example.retina.samples.main.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName

class PostViewModel : ViewModel(){

    var listPost: MutableLiveData<List<Post>> = MutableLiveData()


    fun getPosts():LiveData<List<Post>> = this.listPost
}


