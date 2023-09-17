package com.mbahrami900913.mb_blogapp.data.repository

import com.mbahrami900913.mb_blogapp.data.model.Blog

interface BlogRepository {
    suspend fun getBlogs():List<Blog>
}