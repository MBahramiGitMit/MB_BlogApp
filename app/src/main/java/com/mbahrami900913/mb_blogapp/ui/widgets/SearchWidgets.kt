package com.mbahrami900913.mb_blogapp.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mbahrami900913.mb_blogapp.R
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.ui.theme.cText2
import com.mbahrami900913.mb_blogapp.ui.theme.cText5
import com.mbahrami900913.mb_blogapp.util.Cache
import com.mbahrami900913.mb_blogapp.util.Constants
import com.mbahrami900913.mb_blogapp.util.MyScreens
import com.mbahrami900913.mb_blogapp.util.NetworkChecker
import dev.burnoo.cokoin.navigation.getNavController
@Composable
fun SearchContent(data: List<Blog>, onRequestRefresh: () -> Unit = {}) {
    val context = LocalContext.current
    val navController = getNavController()
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
                    Cache.put(key = Constants.KEY_BLOG, value = it)
                    navController.navigate(MyScreens.BlogScreen.route)
                })
        }
    }
}