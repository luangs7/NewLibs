package com.example.sampleapp.di

import com.example.retina.samples.main.retrofit.ApiInterface
import com.example.sampleapp.data.repository.PostRepository
import com.example.sampleapp.data.repository.PostRepositoryManager
import com.example.sampleapp.main.model.PostViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


    val appModule = module {
        factory { PostRepository(get() as ApiInterface) }
        factory { PostRepositoryManager(get() as PostRepository) }
        viewModel {PostViewModel(get() as PostRepositoryManager)}
    }


