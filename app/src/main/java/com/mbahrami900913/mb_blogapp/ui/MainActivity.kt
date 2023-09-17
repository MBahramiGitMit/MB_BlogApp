package com.mbahrami900913.mb_blogapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mbahrami900913.mb_blogapp.ui.theme.MB_BlogAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MB_BlogAppTheme {

            }
        }
    }
}