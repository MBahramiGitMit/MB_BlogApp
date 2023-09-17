package com.mbahrami900913.mb_blogapp.ui.features

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.data.repository.BlogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(private val blogRepository: BlogRepository) : ViewModel() {
    private val _blogs = MutableStateFlow<List<Blog>>(emptyList())
    private val _isLoading = MutableStateFlow<Boolean>(false)

    val blogs: StateFlow<List<Blog>> = _blogs
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchBlogs()
    }

    fun fetchBlogs() {
        viewModelScope.launch() {
            _isLoading.value = true
            try {
                val blogList = blogRepository.getBlogs()
                _blogs.emit(blogList.shuffled())
            } catch (e: IOException) {
                Log.e("3636", "fetchBlogs: ${e.message}")
            }
            _isLoading.value = false
        }
    }
}