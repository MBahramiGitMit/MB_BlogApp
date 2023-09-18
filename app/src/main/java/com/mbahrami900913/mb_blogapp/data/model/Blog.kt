package com.mbahrami900913.mb_blogapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Blog(
    val __v: Int,
    val _id: String,
    val author: String,
    val category: String,
    val content: String,
    val createdAt: String,
    val image: String,
    val title: String,
    val updatedAt: String
) : Parcelable

data class BlogResponse(
    val blogs: List<Blog>
)