package com.mbahrami900913.mb_blogapp.ui.features

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mbahrami900913.mb_blogapp.ui.theme.cBackground
import com.mbahrami900913.mb_blogapp.ui.widgets.HomeContent
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Composable
fun HomeScreen() {

    val activity = (LocalContext.current) as? Activity
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val vm = getNavViewModel<HomeViewModel>()
    val navController = getNavController()

    val data by vm.blogs.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { /*TODO*/ })
        },
        modifier = Modifier.fillMaxSize(),
        drawerGesturesEnabled = true,
        drawerContent = {

        },
        drawerElevation = 2.dp,
        drawerBackgroundColor = cBackground
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
                HomeContent(
                    data = emptyList(),
                    onRequestRefresh = { vm.fetchBlogs() }
                )
            }
        }

    }
}


