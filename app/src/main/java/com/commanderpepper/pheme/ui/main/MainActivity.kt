package com.commanderpepper.pheme.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.commanderpepper.pheme.ui.theme.PhemeTheme
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
                    MainUI(isExpandedScreen)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainUI(isExpandedScreen: Boolean, mainViewModel: MainViewModel = hiltViewModel()){
    if(isExpandedScreen){
        NavRailLayout(mainViewModel)
    }
    else {
        BottomBarLayout(mainViewModel)
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