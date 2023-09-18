package com.mbahrami900913.mb_blogapp.ui.features

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mbahrami900913.mb_blogapp.ui.widgets.BlogToolbar
import dev.burnoo.cokoin.navigation.getNavController

@Composable
fun BlogScreen() {
    val navController = getNavController()
    var showInfoDialog by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        BlogToolbar(
            title = "fsfsasfdsa",
            onBackClicked = { navController.navigateUp() },
            onInfoClicked = { showInfoDialog = true })
    }) {
        it

    }
}