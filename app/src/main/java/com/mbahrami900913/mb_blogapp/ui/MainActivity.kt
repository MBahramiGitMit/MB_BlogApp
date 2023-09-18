package com.mbahrami900913.mb_blogapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mbahrami900913.mb_blogapp.di.myModules
import com.mbahrami900913.mb_blogapp.ui.features.BlogScreen
import com.mbahrami900913.mb_blogapp.ui.features.home.HomeScreen
import com.mbahrami900913.mb_blogapp.ui.theme.MB_BlogAppTheme
import com.mbahrami900913.mb_blogapp.ui.theme.cBackground
import com.mbahrami900913.mb_blogapp.util.MyScreens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        window.statusBarColor = cBackground.toArgb()

        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                Koin(appDeclaration = {
                    androidContext(this@MainActivity)
                    modules(myModules)
                }) {

                    MB_BlogAppTheme(darkTheme = false) {
                        Surface(color = cBackground, modifier = Modifier.fillMaxSize()) {
                            TeamGitApp()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeamGitApp() {
    val navController = rememberNavController()
    KoinNavHost(
        navController = navController,
        startDestination = MyScreens.HomeScreen.route
    ) {
        composable(route = MyScreens.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = MyScreens.BlogScreen.route) {
            BlogScreen()
        }

    }
}