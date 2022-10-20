package com.commanderpepper.pheme.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commanderpepper.pheme.ui.screens.article.ArticleScreen
import com.commanderpepper.pheme.ui.theme.PhemeTheme
import com.commanderpepper.pheme.uistate.NewsPreviewItem
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Installs a splash screen to be displayed during a cold start
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {

            // If the phone is in landscape mode then the isExpandedScreen boolean is most likely true
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

            PhemeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "articleList") {
                        composable("articleList") {
                            PhemeAppUI { id ->
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhemeTheme {
        NewsPreviewItem(
            NewsPreviewItemUIState(
                "New York Times",
                "John Doe",
                "The Weather",
                "Its sunny",
                "https://i.imgur.com/olisBgy.png",
                0L
            )
        ) {
        }
    }
}