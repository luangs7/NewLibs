package com.example.retina.samples.arch

import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.retina.samples.R
import com.example.retina.samples.main.model.Post
import com.example.retina.samples.main.model.PostViewModel
import com.example.retina.samples.main.retrofit.ApiInterface
import kotlinx.android.synthetic.main.activity_arch.*
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ArchActivity : AppCompatActivity(), onListChanged {

    val api: ApiInterface by inject()
    lateinit var postViewModel: PostViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arch)

        GlobalScope.launch {
            delay(2000)
            retrieveList()
        }




        refresh_btn.setOnClickListener { refreshList() }

    }

    fun retrieveList(){
        api.getPost().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                setupList(response.body())
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.i("error", this@ArchActivity.localClassName)
            }
        })
    }


    fun setupList(list: List<Post>?) {
        postViewModel = ViewModelProviders.of(this@ArchActivity).get(PostViewModel::class.java)
        list?.let {

            postViewModel.listPost.postValue(it)

            postViewModel.getPosts()
                    .observe(this, ListObserver(this))
        }

    }

    fun refreshList() {

        api.getPost().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                response.body()?.let {
                    postViewModel.listPost.postValue(it)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.i("error", this@ArchActivity.localClassName)
            }
        })


    }

    override fun onChangedList(list: List<Post>) {
        Toast.makeText(this@ArchActivity,"Nova lista atualizada!",Toast.LENGTH_SHORT).show()
    }
}

