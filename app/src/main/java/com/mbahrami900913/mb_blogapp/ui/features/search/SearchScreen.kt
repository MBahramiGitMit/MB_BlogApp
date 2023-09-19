package com.mbahrami900913.mb_blogapp.ui.features.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mbahrami900913.mb_blogapp.ui.widgets.SearchContent
import com.mbahrami900913.mb_blogapp.util.Constants
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Composable
fun SearchScreen() {
    val vm = getNavViewModel<SearchViewModel>()
    val navController = getNavController()
    val showFilterDialog by remember { mutableStateOf(false) }

    val data by vm.blogs.collectAsState()
    val categoryList by vm.categoryList.collectAsState()
    val authorList by vm.authors.collectAsState()
    val searchQuery by vm.searchQuery.collectAsState()
    val filtering by vm.filtering.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    val isFilterEnabled by remember { mutableStateOf(filtering != Constants.NO_FILTER) }

    Scaffold(
        topBar = {
            //SearchToolbar()
        },
        modifier = Modifier.fillMaxSize(),

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            } else {
                SearchContent(data=data)
                if (showFilterDialog){
                    //SearchDialog()
                }
            }
        }

    }
}