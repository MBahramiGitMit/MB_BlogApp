package com.mbahrami900913.mb_blogapp.util

import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.util.Constants.CACHE_TIME
import com.mbahrami900913.mb_blogapp.util.Constants.mockArticle


object Cache {
    private val cacheMap = HashMap<String, CacheObject>()
    private const val EXPIRATION_TIME = CACHE_TIME * 60 * 1000 // 1 minutes

    fun put(key: String, value: Blog) {
        cacheMap[key] = CacheObject(value, System.currentTimeMillis())
    }

    fun get(key: String): Blog {
        val value = cacheMap[key]

        if (value != null && System.currentTimeMillis() - value.time < EXPIRATION_TIME) {
            return value.data
        }

        cacheMap.remove(key)
        return mockArticle
    }

    data class CacheObject(val data: Blog, val time: Long)
}