package com.example.sampleapp.data.repository

class PostRepositoryManager(val repository: PostRepository) {

    fun listenResponse() = repository.result

    suspend fun getPostLiveData(numberOfItens: Int) = repository.getPosts(numberOfItens)
    suspend fun getPostLiveDataNow(numberOfItens: Int) = repository.getPostsNow(numberOfItens)

}