package com.mbahrami900913.mb_blogapp.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.ui.theme.cText1
import com.mbahrami900913.mb_blogapp.ui.theme.cText3
import com.mbahrami900913.mb_blogapp.ui.theme.radius4
import com.mbahrami900913.mb_blogapp.util.Constants

@Composable
fun BlogList(modifier: Modifier, data: List<Blog>, onItemClicked: (Blog) -> Unit) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(data.size) { index ->

            Blog(blog = data[index], onClicked = onItemClicked)

        }
    }
}

@Composable
fun Blog(blog: Blog, onClicked: (Blog) -> Unit) {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClicked.invoke(blog) }
            .padding(vertical = 8.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(if (blog.image.startsWith("upload/")) Constants.SITE_BASE_URL + blog.image else blog.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(136.dp, 90.dp)
                .padding(start = 16.dp)
                .clip(radius4),
            contentScale = ContentScale.Crop
        )

        Column() {
            Text(
                text = blog.category,
                textAlign = TextAlign.Right,
                color = cText3,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(start = 18.dp, top = 8.dp)
            )
            Text(
                text = blog.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Right,
                color = cText1,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 18.dp, top = 2.dp, end = 16.dp)
            )
        }

    }
}