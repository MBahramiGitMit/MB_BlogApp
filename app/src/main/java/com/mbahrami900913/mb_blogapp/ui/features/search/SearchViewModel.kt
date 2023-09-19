package com.mbahrami900913.mb_blogapp.ui.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.data.model.Category
import com.mbahrami900913.mb_blogapp.data.model.Filtering
import com.mbahrami900913.mb_blogapp.data.repository.BlogRepository
import com.mbahrami900913.mb_blogapp.util.Constants.NO_FILTER
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val blogRepository: BlogRepository) : ViewModel() {
    private val _blogs = MutableStateFlow<List<Blog>>(emptyList())
    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _authors = MutableStateFlow((mutableListOf<String>()))
    private val _filtering = MutableStateFlow(NO_FILTER)
    private val _isLoading = MutableStateFlow(false)

    val blogs: StateFlow<List<Blog>> = _blogs
    val categoryList: StateFlow<List<Category>> = _categoryList
    val searchQuery: StateFlow<String> = _searchQuery
    val authors: StateFlow<List<String>> = _authors
    val filtering: StateFlow<Filtering> = _filtering
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchBlogs()
        fetchCategoryList()
    }

    fun fetchBlogs() {
        viewModelScope.launch() {
            _isLoading.value = true
            try {
                val fullBlogList = blogRepository.getBlogs()

                val blogsFilteredByCategory: List<Blog> =
                    if (_filtering.value.categories.isNotEmpty()) {
                        fullBlogList.filter { _filtering.value.categories.contains(it.category) }
                    } else {
                        fullBlogList
                    }

                val blogsFilteredByAuthor = if (_filtering.value.authors.isNotEmpty()) {
                    blogsFilteredByCategory.filter { _filtering.value.authors.contains(it.category) }
                } else {
                    blogsFilteredByCategory
                }

                val blogsFilteredBySearchQuery: List<Blog> = if (_searchQuery.value.isNotEmpty()) {
                    blogsFilteredByAuthor.filter {
                        it.title.contains(_searchQuery.value) ||
                                it.content.contains(_searchQuery.value)
                    }
                } else {
                    blogsFilteredByAuthor
                }

                _blogs.emit(blogsFilteredBySearchQuery)
                fetchAuthors()

            } catch (e: Exception) {
                Log.e("3636", "fetchBlogs: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchCategoryList() {
        viewModelScope.launch() {
            try {
                val categoryList = blogRepository.getCategoryList()
                _categoryList.emit(categoryList)
            } catch (e: Exception) {
                Log.e("3636", "fetchBlogs: ${e.message}")
            }
        }
    }

    fun fetchAuthors() {
        if (_blogs.value.isNotEmpty()) {
            _blogs.value.forEach {
                if (!_authors.value.contains(it.author)) {
                    _authors.value.add(it.author)
                }
            }
        }
    }

    fun setSearchQuery(srt: String) {
        _searchQuery.value = srt
        fetchBlogs()
    }

    fun changeFiltering(filtering: Filtering) {
        _filtering.value = filtering
        fetchBlogs()
    }
}