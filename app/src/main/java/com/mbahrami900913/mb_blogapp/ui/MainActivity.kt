package com.mbahrami900913.mb_blogapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.mbahrami900913.mb_blogapp.di.myModules
import com.mbahrami900913.mb_blogapp.ui.theme.MB_BlogAppTheme
import dev.burnoo.cokoin.Koin
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        setContent {
            MB_BlogAppTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                    Koin(appDeclaration = {
                        androidContext(this@MainActivity)
                        modules(myModules)
                    }) {

                    }

                }
            }
        }
    }
}