package com.mbahrami900913.mb_blogapp.util

import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.data.model.Filtering

object Constants {
    const val SITE_BASE_URL = "https://team-git.iran.liara.run/"
    const val CACHE_TIME = 120
    const val KEY_BLOG = "keyBlogScreen"

    val NO_FILTER = Filtering(emptyList(), emptyList())

    val mockArticle = Blog(
        _id = "-1",
        author = "",
        title = "",
        category = "",
        content = "",
        createdAt = "",
        image = "",
        updatedAt = "",
        __v = 0
    )
}