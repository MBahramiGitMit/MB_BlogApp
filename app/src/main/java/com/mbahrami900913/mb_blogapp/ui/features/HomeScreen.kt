package com.mbahrami900913.mb_blogapp.ui.features

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Composable
fun HomeScreen() {
    val vm= getNavViewModel<HomeViewModel>()
    val navController= getNavController()

    val data by vm.blogs.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    Log.i("3636", "HomeScreen: $data")
}