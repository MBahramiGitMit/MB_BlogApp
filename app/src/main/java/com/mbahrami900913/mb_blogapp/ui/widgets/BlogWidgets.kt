package com.mbahrami900913.mb_blogapp.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mbahrami900913.mb_blogapp.R
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.ui.theme.cBackground
import com.mbahrami900913.mb_blogapp.ui.theme.cText1
import com.mbahrami900913.mb_blogapp.ui.theme.cText3
import com.mbahrami900913.mb_blogapp.ui.theme.radius3
import com.mbahrami900913.mb_blogapp.ui.theme.radius4
import com.mbahrami900913.mb_blogapp.util.Constants.SITE_BASE_URL
import com.mbahrami900913.mb_blogapp.util.toParagraph

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
                .data(if (blog.image.startsWith("upload/")) SITE_BASE_URL + blog.image else blog.image)
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

@Composable
fun BlogToolbar(title: String, onBackClicked: () -> Unit, onInfoClicked: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(cBackground)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainButton(modifier = Modifier, src = R.drawable.ic_arrow_right) {
            onBackClicked.invoke()
        }

        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp).weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )

        MainButton(modifier = Modifier, src = R.drawable.ic_info) {
            onInfoClicked.invoke()
        }
    }
}

@Composable
fun BlogContent(blog: Blog, onImageClicked: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    val paragraphStyle = ParagraphStyle(textAlign = TextAlign.Justify)
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(color = cText1)) {
            append(blog.content.toParagraph(3))
        }
        addStyle(paragraphStyle, 0, length)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val width = 328
        val height = 246
        val aspectRatio = width.toFloat() / height.toFloat()
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(if (blog.image.startsWith("upload/")) SITE_BASE_URL + blog.image else blog.image)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .clip(radius3)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onImageClicked.invoke()
                },
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            text = annotatedText,
            color = cText1,
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.body1,
        )

    }

}