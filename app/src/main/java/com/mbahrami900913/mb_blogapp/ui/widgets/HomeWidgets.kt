package com.mbahrami900913.mb_blogapp.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.util.FadeInOutWidget
import com.mbahrami900913.mb_blogapp.util.NetworkChecker
import kotlinx.coroutines.delay
import com.mbahrami900913.mb_blogapp.R
import com.mbahrami900913.mb_blogapp.ui.theme.*

@Composable
fun SnackBar(title: String) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(true) { isVisible = true }

    FadeInOutWidget(isVisible) {

        Snackbar(
            modifier = Modifier.padding(16.dp),
            backgroundColor = cError
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center
            )
        }

        LaunchedEffect(true) {
            delay(2500)
            isVisible = false
        }
    }
}

@Composable
fun HomeContent(data: List<Blog>, onRequestRefresh: () -> Unit) {
    val context = LocalContext.current
    if (!NetworkChecker(context).isInternetConnected)
        SnackBar(title = "لطفا از اتصال اینترنت خود مطمعن شوید.")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (data.isEmpty()) {
            Column(
                modifier = Modifier.padding(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.ic_no_article),
                    contentDescription = null
                )
                Text(
                    text = "مقاله ای برای نمایش وجود ندارد.",
                    style = MaterialTheme.typography.h5,
                    color = cText2
                )
                TextButton(onClick = { onRequestRefresh.invoke() }) {
                    Text(
                        text = "بارگذاری مجدد",
                        style = MaterialTheme.typography.caption,
                        color = cText5
                    )
                }
            }
        } else {
            BlogList(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                data = data,
                onItemClicked = {
                    // TODO: navigate to blog screen
                })
        }
    }
}

@Composable
fun HomeToolbar(onDrawerClicked: () -> Unit, onSearchClicked: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(cBackground)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainButton(modifier = Modifier, src = R.drawable.ic_menu) {
            onDrawerClicked.invoke()
        }

        Image(painter = painterResource(id = R.drawable.ic_dunijet), contentDescription = null)

        MainButton(modifier = Modifier, src = R.drawable.ic_search) {
            onSearchClicked.invoke()
        }
    }
}