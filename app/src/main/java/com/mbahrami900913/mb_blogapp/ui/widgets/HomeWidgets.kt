package com.mbahrami900913.mb_blogapp.ui.widgets

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.util.FadeInOutWidget
import com.mbahrami900913.mb_blogapp.util.NetworkChecker
import kotlinx.coroutines.delay
import com.mbahrami900913.mb_blogapp.R
import com.mbahrami900913.mb_blogapp.ui.theme.*
import kotlinx.coroutines.launch

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

@Composable
fun HomeDrawer(onCloseDrawer: () -> Unit) {
    BackHandler { onCloseDrawer() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.End
    ) {
        MainButton(
            modifier = Modifier.padding(top = 14.dp, end = 16.dp),
            src = R.drawable.ic_close
        ) {
            onCloseDrawer.invoke()
        }
        Spacer(modifier = Modifier.height(20.dp))
        DrawerBody(modifier = Modifier)
    }
}

@Composable
fun DrawerBody(modifier: Modifier) {
    Column(modifier = modifier) {

        DrawerMenuItem(
            iconDrawableId = R.drawable.ic_code,
            text = "اطلاعات توسعه دهندگان"
        )

        DrawerMenuItem(
            iconDrawableId = R.drawable.ic_info,
            text = "درباره برنامه",
        )


    }
}

@SuppressLint("RememberReturnType")
@Composable
private fun DrawerMenuItem(
    iconDrawableId: Int,
    text: String
) {

    var rotation by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    val rotationAnimation = remember { Animatable(0f) }

    Column {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                ) {

                    scope.launch {

                        if (rotation == 0f) {
                            rotation = -90f

                            rotationAnimation.animateTo(
                                targetValue = -90f, animationSpec = tween(durationMillis = 300)
                            )
                        } else {
                            rotation = 0f

                            rotationAnimation.animateTo(
                                targetValue = 0f, animationSpec = tween(durationMillis = 300)
                            )
                        }

                    }

                }
                .padding(top = 14.dp, bottom = 14.dp)
        ) {

            val (title, arrow, detail) = createRefs()

            Row(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth()
                    .padding(start = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(iconDrawableId),
                    tint = cPrimary,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.subtitle2
                )

            }

            Icon(
                modifier = Modifier
                    .constrainAs(arrow) {
                        end.linkTo(parent.end, 25.dp)
                        top.linkTo(title.top)
                        bottom.linkTo(title.bottom)
                    }
                    .size(15.dp, 18.dp)
                    .rotate(rotationAnimation.value),
                tint = cArrow,
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = null,
            )

        }

        AnimatedVisibility(
            visible = rotation == -90f,

            enter = slideInHorizontally(
                initialOffsetX = { 30 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeIn(animationSpec = tween(durationMillis = 300)),

            exit = slideOutVertically(
                targetOffsetY = { 30 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeOut(animationSpec = tween(durationMillis = 300))

        ) {

            if (text == "اطلاعات توسعه دهندگان") {
                DevelopersIds()
            } else {
//                AppInfo(
//                    modifier = Modifier.padding(
//                        top = 8.dp,
//                        start = 18.dp,
//                        end = 25.dp,
//                        bottom = 16.dp
//                    )
//                )
            }
        }
    }
}
@Composable
fun DevelopersIds() {

    Column {
        Developer(R.drawable.amir_mohammadi, "امیرحسین محمدی", "برنامه نویس اپ اندروید", "@dunijet")
        Developer(R.drawable.omid_baharifar, "امید بهاری فر", "برنامه نویس پنل فرانت", "@weblax_ir")
        Developer(R.drawable.erfan_yousefi, "عرفان یوسفی", "برنامه نویس بک اند", "@erfanyousefi.ir")
        Developer(R.drawable.soroush_mosapoor, "سروش موسی\u200Cپور", "متخصص دِوآپس", "@codingcogs")
    }

}
@SuppressLint("RememberReturnType")
@Composable
private fun Developer(
    iconDrawableId: Int,
    title: String,
    detail: String,
    page: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val uriHandler = LocalUriHandler.current

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 18.dp)
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            uriHandler.openUri("https://www.instagram.com/" + page.substring(1))
        }) {

        val (nameDeveloper, imgInsta, details) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(nameDeveloper) {
                    top.linkTo(imgInsta.top)
                    start.linkTo(imgInsta.end)
                }
                .padding(start = 18.dp),
            text = title,
            color = cText1,
            style = MaterialTheme.typography.h4
        )

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

            Text(
                modifier = Modifier
                    .constrainAs(details) {
                        top.linkTo(nameDeveloper.bottom)
                        start.linkTo(imgInsta.end)
                    }
                    .padding(end = 18.dp, top = 2.dp),
                text = detail + " - " + page,
                color = cText3,
                style = MaterialTheme.typography.overline
            )


        }

        Box(modifier = Modifier
            .constrainAs(imgInsta) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
            .padding(start = 16.dp), contentAlignment = Alignment.Center)
        {

            Image(
                modifier = Modifier.size(52.dp),
                painter = painterResource(id = R.drawable.ic_ring),
                contentDescription = null
            )

            Image(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                painter = painterResource(id = iconDrawableId),
                contentDescription = null
            )

        }
    }
}