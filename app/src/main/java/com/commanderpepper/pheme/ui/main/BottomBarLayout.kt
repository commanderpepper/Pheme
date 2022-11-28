package com.commanderpepper.pheme.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.ui.screens.article.ArticleScreen
import com.commanderpepper.pheme.ui.screens.articlelist.Articles
import com.commanderpepper.pheme.ui.uistate.CategoryButtonUIState

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomBarLayout(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    val bottomNavBarIcons = listOf(
        R.drawable.ic_news,
        R.drawable.ic_sports,
        R.drawable.ic_technology,
        R.drawable.ic_entertainment,
    )

    val navContentDescription = listOf(
        R.string.home_activity_bottom_bar_news_content_description,
        R.string.home_activity_bottom_bar_sports_content_description,
        R.string.home_activity_bottom_bar_technology_content_description,
        R.string.home_activity_bottom_bar_entertainment_content_description
    )

    val categories = Category.values()

    val barIcons = List(4){ i ->
        CategoryButtonUIState(
            category = categories[i],
            resourceId = bottomNavBarIcons[i],
            contentDescriptionId = navContentDescription[i]
        )
    }

    val category = mainViewModel.categoryFlow.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar() {
                barIcons.forEach {
                    NavigationBarItem(
                        selected = category.value == it.category,
                        onClick = {
                            mainViewModel.updateCategory(it.category)
                            navController.navigate(
                                "articles/{category}".replace(
                                    oldValue = "{category}",
                                    newValue = category.value.category
                                )
                            )
                        },
                        icon = {
                            val image = painterResource(id = it.resourceId)
                            val contentDescription = stringResource(id = it.contentDescriptionId)
                            Icon(modifier = Modifier.align(Alignment.CenterVertically), painter = image, contentDescription = contentDescription)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = "articles/{category}" ){
            composable("articles/{category}"){
                Articles(
                    modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding() / 2),
                    mainViewModel = mainViewModel
                ){ id ->
                    navController.navigate(
                        "article/{articleId}".replace(
                            oldValue = "{articleId}",
                            newValue = "$id"
                        )
                    )
                }
            }
            composable(
                route = "article/{articleId}",
            ) { entry ->
                val articleId: Long =
                    entry.arguments?.getString("articleId", "-1")!!.toLong()
                ArticleScreen(
                    articleId = articleId
                ){
                    navController.popBackStack()
                }
            }
        }
    }
}