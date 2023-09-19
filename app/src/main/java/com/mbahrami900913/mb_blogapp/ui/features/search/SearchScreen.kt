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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mbahrami900913.mb_blogapp.ui.widgets.SearchContent
import com.mbahrami900913.mb_blogapp.ui.widgets.SearchDialog
import com.mbahrami900913.mb_blogapp.ui.widgets.SearchToolbar
import com.mbahrami900913.mb_blogapp.util.Constants
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Composable
fun SearchScreen() {
    val vm = getNavViewModel<SearchViewModel>()
    val navController = getNavController()
    var showFilterDialog by remember { mutableStateOf(false) }

    val data by vm.blogs.collectAsState()
    val categoryList by vm.categoryList.collectAsState()
    val authorList by vm.authors.collectAsState()
    val searchQuery by vm.searchQuery.collectAsState()
    val filtering by vm.filtering.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    var isFilterEnabled by remember { mutableStateOf(filtering != Constants.NO_FILTER) }

    Scaffold(
        topBar = {
            SearchToolbar(
                edtValue = searchQuery,
                isFilteringEnabled = isFilterEnabled,
                onBackPressed = { navController.navigateUp() },
                onEditChange = {
                    vm.setSearchQuery(it)
                },
                onFilterClicked = { showFilterDialog = true })
        },
        modifier = Modifier.fillMaxSize(),

        ) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValue.calculateTopPadding()),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            } else {
                SearchContent(data = data)
                if (showFilterDialog) {
                    SearchDialog(
                        filtering = filtering,
                        categoryList = categoryList,
                        authorList = authorList,
                        onDismissClicked = { showFilterDialog = false },
                        onSubmitClicked = {
                            isFilterEnabled= !(it.authors.isEmpty() && it.categories.isEmpty())
                            vm.changeFiltering(it)
                        }
                    )
                }
            }
        }

    }
}