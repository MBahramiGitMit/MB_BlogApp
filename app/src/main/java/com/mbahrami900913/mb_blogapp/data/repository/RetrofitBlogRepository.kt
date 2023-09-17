package com.mbahrami900913.mb_blogapp.data.repository

import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.data.net.ApiService

class RetrofitBlogRepository(private val apiService: ApiService):BlogRepository {
    override suspend fun getBlogs(): List<Blog> {
        return apiService.getBlogs().blogs
    }
}