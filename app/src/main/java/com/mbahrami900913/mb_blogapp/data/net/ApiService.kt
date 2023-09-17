package com.mbahrami900913.mb_blogapp.data.net

import com.mbahrami900913.mb_blogapp.data.model.BlogResponse
import com.mbahrami900913.mb_blogapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("blog")
    suspend fun getBlogs(): BlogResponse
}

fun createApiService(): ApiService {
    val retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.SITE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    return retrofit.create(ApiService::class.java)
}