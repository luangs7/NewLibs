package com.example.sampleapp

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import br.com.luan2.lgutilsk.utils.createSnackProgress
import br.com.luan2.lgutilsk.utils.dismissSnackProgress
import br.com.luan2.lgutilsk.utils.showError
import com.example.retina.samples.main.model.Post
import com.example.sampleapp.data.Resource
import com.example.sampleapp.data.Status
import com.example.sampleapp.main.model.PostViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val postViewModel by viewModel<PostViewModel>()
    lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupViewModel()

        postViewModel.getPosts(60)
    }

    fun setupViewModel() {
        postViewModel.repositoryResult.observe(this, Observer {
            it?.let { resource -> handleResult(resource) }
        })

        postViewModel.innerRepositoryResult.observe(this, Observer {
            it?.let { resource -> handleResult(resource) }
        })
    }


    fun handleResult(result: Resource<List<Post>>) {
        when (result.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> result?.data?.let {
                showData(it)
            }
            Status.ERROR -> showError(result.message!!)
        }
    }

    fun showLoading(){
        snackbar = createSnackProgress("Buscando dados...")
        snackbar.show()
    }

    fun showData(data:List<Post>){
        Log.i("Posts: ", data.joinToString("..."))
        snackbar.dismissSnackProgress(this@MainActivity)
    }
}
