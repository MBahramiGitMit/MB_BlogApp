package com.mbahrami900913.mb_blogapp.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mbahrami900913.mb_blogapp.R
import com.mbahrami900913.mb_blogapp.data.model.Blog
import com.mbahrami900913.mb_blogapp.data.model.Category
import com.mbahrami900913.mb_blogapp.data.model.Filtering
import com.mbahrami900913.mb_blogapp.ui.theme.cBackground
import com.mbahrami900913.mb_blogapp.ui.theme.cError
import com.mbahrami900913.mb_blogapp.ui.theme.cPrimary
import com.mbahrami900913.mb_blogapp.ui.theme.cTabNotSelected
import com.mbahrami900913.mb_blogapp.ui.theme.cText1
import com.mbahrami900913.mb_blogapp.ui.theme.cText2
import com.mbahrami900913.mb_blogapp.ui.theme.cText3
import com.mbahrami900913.mb_blogapp.ui.theme.cText5
import com.mbahrami900913.mb_blogapp.ui.theme.cTextFieldBackground
import com.mbahrami900913.mb_blogapp.ui.theme.cTextFieldContent
import com.mbahrami900913.mb_blogapp.ui.theme.cWhite
import com.mbahrami900913.mb_blogapp.util.Action
import com.mbahrami900913.mb_blogapp.util.Cache
import com.mbahrami900913.mb_blogapp.util.Constants
import com.mbahrami900913.mb_blogapp.util.Constants.NO_FILTER
import com.mbahrami900913.mb_blogapp.util.MyScreens
import com.mbahrami900913.mb_blogapp.util.NetworkChecker
import com.mbahrami900913.mb_blogapp.util.customTabIndicatorOffset
import com.mbahrami900913.mb_blogapp.util.getCurrentOrientation
import dev.burnoo.cokoin.navigation.getNavController
import kotlinx.coroutines.launch

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

@Composable
fun CustomCheckBox(title: String, checked: Boolean, onChange: (Action, String) -> Unit) {
    var isChecked by remember { mutableStateOf(checked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(start = 16.dp, top = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                isChecked = !isChecked
                onChange.invoke(if (isChecked) Action.Inserted else Action.Deleted, title)
            }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                onChange.invoke(if (isChecked) Action.Inserted else Action.Deleted, title)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = cPrimary,
                uncheckedColor = cText3
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = title, style = MaterialTheme.typography.subtitle2, color = cText1)
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable
fun CategoryPageUi(
    fullList: List<Category>,
    categoryChecked: List<String>,
    onCategoryChanged: (Action, String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(fullList.size) { item ->
            CustomCheckBox(
                title = fullList[item].name,
                checked = categoryChecked.contains(fullList[item].name),
                onChange = onCategoryChanged
            )
        }
    }
}

@Composable
fun AuthorPageUi(
    fullList: List<String>,
    authorNameChecked: List<String>,
    onAuthorChanged: (Action, String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(fullList.size) { item ->
            CustomCheckBox(
                title = fullList[item],
                checked = authorNameChecked.contains(fullList[item]),
                onChange = onAuthorChanged
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchDialog(
    filtering: Filtering,
    categoryList: List<Category>,
    authorList: List<String>,
    onDismissClicked: () -> Unit,
    onSubmitClicked: (Filtering) -> Unit
) {
    val orientation = getCurrentOrientation()

    val tabData = listOf(
        Pair("دسته بندی", R.drawable.dialog_category),
        Pair("نویسندگان", R.drawable.dialog_tag),
    )

    val pagerState = rememberPagerState(
        pageCount = tabData.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()

    val selectedCategories = remember { mutableStateListOf<String>() }
    val selectedAuthors = remember { mutableStateListOf<String>() }
    selectedCategories.addAll(filtering.categories)
    selectedAuthors.addAll(filtering.authors)

    val currentTabIndex by rememberUpdatedState(newValue = tabIndex)
    Dialog(onDismissRequest = onDismissClicked) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(634.dp),
            shape = RoundedCornerShape(size = 16.dp)
        ) {

            Column(
                modifier = if (orientation == 1) Modifier.verticalScroll(rememberScrollState()) else Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {

                    // TAB
                    TabRow(
                        contentColor = cPrimary,
                        backgroundColor = cWhite,
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier.background(cBackground),
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier.customTabIndicatorOffset(
                                    currentTabPosition = tabPositions[tabIndex],
                                    tabWidth = 80.dp,
                                    tabHeight = 3.dp,
                                    topCornerRadius = 4.dp
                                )
                            )
                        },
                        divider = {
                            TabRowDefaults.Divider(
                                thickness = 2.dp,
                                color = cTextFieldBackground
                            )
                        }
                    ) {
                        tabData.forEachIndexed { index, tabPair ->
                            Tab(
                                selected = currentTabIndex == index,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = {
                                    Text(
                                        modifier = Modifier.padding(top = 4.dp),
                                        text = tabPair.first,
                                        color = if (tabIndex == index) cPrimary else cTabNotSelected,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.overline,
                                    )
                                },
                                icon = {
                                    Icon(
                                        tint = if (tabIndex == index) cPrimary else cTabNotSelected,
                                        modifier = Modifier.size(20.dp),
                                        painter = painterResource(id = tabPair.second),
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }

                    // PAGER
                    HorizontalPager(
                        state = pagerState
                    ) { index ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp),
                        ) {

                            if (tabData[index].first == "دسته بندی") {

                                Spacer(modifier = Modifier.height(8.dp))
                                CategoryPageUi(
                                    categoryList,
                                    selectedCategories
                                ) { action, categoryTitle ->

                                    if (action == Action.Inserted)
                                        selectedCategories.add(categoryTitle)
                                    else
                                        selectedCategories.removeIf { it == categoryTitle }

                                }

                            } else {

                                Spacer(modifier = Modifier.height(8.dp))
                                AuthorPageUi(authorList, selectedAuthors) { action, authorName ->
                                    if (action == Action.Inserted)
                                        selectedAuthors.add(authorName)
                                    else
                                        selectedAuthors.removeIf { it == authorName }
                                }
                            }
                        }
                    }
                }
                Column {
                    Button(
                        elevation = ButtonDefaults.elevation(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(), modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(start = 4.dp, end = 4.dp)
                            .border(
                                width = 1.5.dp,
                                color = cTextFieldBackground,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {
                            selectedCategories.clear()
                            selectedAuthors.clear()
                            onSubmitClicked.invoke(NO_FILTER)
                            onDismissClicked.invoke()
                        }) {
                        Text(
                            text = "حذف فیلترها",
                            color = cError,
                            style = MaterialTheme.typography.button
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(start = 4.dp, end = 4.dp),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {
                            onSubmitClicked.invoke(
                                Filtering(
                                    categories = selectedCategories,
                                    authors = selectedAuthors
                                )
                            )
                            onDismissClicked.invoke()
                        }) {
                        Text(
                            text = "اعمال فیلترها",
                            color = Color.White,
                            style = MaterialTheme.typography.button
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}