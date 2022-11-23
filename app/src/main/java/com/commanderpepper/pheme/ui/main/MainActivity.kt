package com.commanderpepper.pheme.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.ui.screens.article.ArticleScreen
import com.commanderpepper.pheme.ui.screens.articlelist.Articles
import com.commanderpepper.pheme.ui.theme.PhemeTheme
import com.commanderpepper.pheme.ui.uistate.CategoryButtonUIState
import com.commanderpepper.pheme.ui.uistate.NewsPreviewItem
import com.commanderpepper.pheme.ui.uistate.NewsPreviewItemUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Installs a splash screen to be displayed during a cold start
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {

            // If the phone is in landscape mode then the isExpandedScreen boolean is most likely true
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

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

            var category by rememberSaveable{mutableStateOf(Category.NEWS)}

            PhemeTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Scaffold(
                        bottomBar = {
                            BottomAppBar() {
                                barIcons.forEach {
                                    NavigationBarItem(
                                        selected = category == it.category,
                                        onClick = {
                                            category = it.category
                                            navController.navigate(
                                                "articles/{category}".replace(
                                                    oldValue = "{category}",
                                                    newValue = category.category
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
                                    modifier = Modifier.padding(paddingValues)
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
                                    isExpandedScreen = isExpandedScreen,
                                    articleId = articleId
                                ){
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhemeTheme {
        NewsPreviewItem(
            NewsPreviewItemUIState(
                "New York Times",
                "John Doe",
                "The Weather",
                "https://i.imgur.com/olisBgy.png",
                0L
            )
        ) {
        }
    }
}