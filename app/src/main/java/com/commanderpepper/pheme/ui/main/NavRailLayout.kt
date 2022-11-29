package com.commanderpepper.pheme.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commanderpepper.pheme.ui.screens.article.ArticleScreen
import com.commanderpepper.pheme.ui.screens.articlelist.Articles
import com.commanderpepper.pheme.ui.util.createCategoryButtonUIStates

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavRailLayout(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val categoryButtonUIStateList = createCategoryButtonUIStates()
    val category = mainViewModel.categoryFlow.collectAsState()
    var articleId by remember { mutableStateOf(-1L) }

    fun articleClicked(id: Long){
        articleId = id
    }

    NavHost(navController = navController, startDestination = "articles/{category}"){
        composable("articles/{category}"){
            Row() {
                NavigationRail() {
                    Spacer(Modifier.weight(1f))
                    categoryButtonUIStateList.forEach { categoryButtonUIState ->
                        NavigationRailItem(
                            selected = categoryButtonUIState.category == category.value,
                            onClick = {
                                if(categoryButtonUIState.category != category.value){
                                    mainViewModel.updateCategory(categoryButtonUIState.category)
                                    navController.navigate(
                                        "articles/{category}".replace(
                                            oldValue = "{category}",
                                            newValue = category.value.category
                                        )
                                    )
                                }
                            },
                            icon = {
                                val image = painterResource(id = categoryButtonUIState.resourceId)
                                val contentDescription = stringResource(id = categoryButtonUIState.contentDescriptionId)
                                Icon(painter = image, contentDescription = contentDescription)
                            })
                    }
                    Spacer(Modifier.weight(1f))
                }
                Articles(modifier = Modifier.weight(6 / 10f), mainViewModel = mainViewModel) {
                    articleClicked(it)
                }
                if(articleId != -1L){
                    ArticleScreen(modifier = Modifier.weight(4 / 10f), articleId = articleId, showTopBar = false) {

                    }
                }
            }
        }
    }
}