package com.mbahrami900913.mb_blogapp.di

import com.mbahrami900913.mb_blogapp.data.net.createApiService
import com.mbahrami900913.mb_blogapp.data.repository.BlogRepository
import com.mbahrami900913.mb_blogapp.data.repository.RetrofitBlogRepository
import com.mbahrami900913.mb_blogapp.ui.features.home.HomeViewModel
import com.mbahrami900913.mb_blogapp.ui.features.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules= module {
    single { createApiService() }
    single<BlogRepository> { RetrofitBlogRepository(get()) }

    viewModel{ HomeViewModel(get()) }
    viewModel{ SearchViewModel(get()) }
}