package com.mbahrami900913.mb_blogapp.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mbahrami900913.mb_blogapp.R
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.ui.theme.cPrimary
import com.mbahrami900913.mb_blogapp.ui.theme.cText1
import com.mbahrami900913.mb_blogapp.ui.theme.cText2
import com.mbahrami900913.mb_blogapp.ui.theme.cText5
import com.mbahrami900913.mb_blogapp.ui.theme.cTextFieldBackground
import com.mbahrami900913.mb_blogapp.ui.theme.cTextFieldContent
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

@Composable
fun SearchToolbar(
    edtValue: String,
    isFilteringEnabled: Boolean,
    onEditChange: (String) -> Unit,
    onFilterClicked: () -> Unit,
    onBackPressed: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPressed, modifier = Modifier.padding(horizontal = 4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                modifier = Modifier.rotate(180f)
            )
        }
        TextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            textStyle = MaterialTheme.typography.button,
            placeholder = {
                Text(
                    color = cTextFieldContent,
                    text = "عنوان مقاله را جستجو کنید",
                    style = MaterialTheme.typography.button
                )
            },
            value = edtValue,
            singleLine = true,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    tint = cTextFieldContent,
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(47.dp)
                        .padding(end = 3.dp)
                        .background(
                            if (isFilteringEnabled) cPrimary else Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onFilterClicked.invoke()
                        }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        tint = if (isFilteringEnabled) Color.White else cText1,
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = null,
                    )
                }
            },
            onValueChange = onEditChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(end = 16.dp)
                .weight(1f),
            colors = TextFieldDefaults.textFieldColors(
                textColor = cText1,
                backgroundColor = cTextFieldBackground,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(14.dp),
        )
    }
}