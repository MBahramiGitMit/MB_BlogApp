package com.mbahrami900913.mb_blogapp.ui.features

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mbahrami900913.mb_blogapp.ui.widgets.BlogToolbar
import com.mbahrami900913.mb_blogapp.util.Cache
import com.mbahrami900913.mb_blogapp.util.Constants
import dev.burnoo.cokoin.navigation.getNavController

@Composable
fun BlogScreen() {
    val navController = getNavController()
    var showInfoDialog by remember { mutableStateOf(false) }
    val blog = Cache.get(Constants.KEY_BLOG)
    Scaffold(topBar = {
        BlogToolbar(
            title = blog.title,
            onBackClicked = { navController.navigateUp() },
            onInfoClicked = { showInfoDialog = true })
    }) {
        it

    }
}