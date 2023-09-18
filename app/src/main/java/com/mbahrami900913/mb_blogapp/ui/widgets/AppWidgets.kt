package com.mbahrami900913.mb_blogapp.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mbahrami900913.mb_blogapp.ui.theme.cBorder
import com.mbahrami900913.mb_blogapp.ui.theme.radius2

@Composable
fun MainButton(modifier: Modifier, src: Int, onButtonClicked: () -> Unit) {

    Box(
        modifier = modifier
            .size(41.dp)
            .border(width = (1.7).dp, color = cBorder, shape = radius2)
            .clip(radius2)
            .clickable { onButtonClicked.invoke() }
    ) {
        Icon(
            painter = painterResource(id = src),
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp))
    }
}