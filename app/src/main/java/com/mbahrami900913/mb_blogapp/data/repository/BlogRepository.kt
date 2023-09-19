package com.mbahrami900913.mb_blogapp.data.repository

import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.data.model.Category

interface BlogRepository {
    suspend fun getBlogs(): List<Blog>
    suspend fun getCategoryList(): List<Category>
}