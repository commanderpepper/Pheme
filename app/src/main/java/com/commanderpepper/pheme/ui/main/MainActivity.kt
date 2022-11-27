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

            PhemeTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    if(isExpandedScreen){
                        NavRailLayout()
                    }
                    else {
                        BottomBarLayout()
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