package com.commanderpepper.pheme.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.ui.screens.articlelist.Articles
import com.commanderpepper.pheme.ui.uistate.CategoryButtonUIState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavRailLayout() {
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

    var category by rememberSaveable{ mutableStateOf(Category.NEWS) }

    fun articleClicked(id: Long){

    }

    NavHost(navController = navController, startDestination = "articles/{category}"){
        composable("articles/{category}"){
            Row() {
                NavigationRail() {
                    Spacer(Modifier.weight(1f))
                    barIcons.forEach { categoryButtonUIState ->
                        NavigationRailItem(
                            selected = categoryButtonUIState.category == category,
                            onClick = {
                                category = categoryButtonUIState.category
                                navController.navigate(
                                    "articles/{category}".replace(
                                        oldValue = "{category}",
                                        newValue = category.category
                                    )
                                )
                            },
                            icon = {
                                val image = painterResource(id = categoryButtonUIState.resourceId)
                                val contentDescription = stringResource(id = categoryButtonUIState.contentDescriptionId)
                                Icon(painter = image, contentDescription = contentDescription)
                            })
                    }
                    Spacer(Modifier.weight(1f))
                }
                Articles() {
                    articleClicked(it)
                }

            }
        }
    }
}