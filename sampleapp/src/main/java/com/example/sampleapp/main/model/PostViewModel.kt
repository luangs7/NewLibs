package com.example.sampleapp.main.model


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.retina.samples.main.model.Post
import com.example.sampleapp.data.Resource
import com.example.sampleapp.data.Status
import com.example.sampleapp.data.repository.PostRepository
import com.example.sampleapp.data.repository.PostRepositoryManager
import kotlinx.coroutines.launch

class PostViewModel(val manager: PostRepositoryManager) : BaseViewModel() {

    val innerRepositoryResult = MutableLiveData<Resource<List<Post>>>()
    val innerResult
        get() = innerRepositoryResult as LiveData<Resource<List<Post>>>

    val repositoryResult = manager.listenResponse()

    fun getPosts(numberOfItens: Int) {
        this.launch(context = coroutineContext) {
            manager.getPostLiveData(numberOfItens)
        }
    }

    fun getPostsNow(numberOfItens: Int) {
        this.innerRepositoryResult.value = Resource.loading(null)
        this.launch(context = coroutineContext) {
            val response = manager.getPostLiveDataNow(numberOfItens)
            innerRepositoryResult.postValue(response)
        }
    }


}