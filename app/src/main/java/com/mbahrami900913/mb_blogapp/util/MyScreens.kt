package com.mbahrami900913.mb_blogapp.util

sealed class MyScreens(val route: String) {
    data object HomeScreen : MyScreens(route = "homeScreen")
    data object BlogScreen : MyScreens(route = "blogScreen")
    data object LargeImageScreen : MyScreens(route = "largeImageScreen")
    data object SearchScreen : MyScreens(route = "searchScreen")
}
