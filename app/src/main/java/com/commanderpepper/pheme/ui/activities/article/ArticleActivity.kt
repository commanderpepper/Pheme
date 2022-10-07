package com.commanderpepper.pheme.ui.activities.article

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.commanderpepper.pheme.ui.activities.article.ui.DisplayArticle
import com.commanderpepper.pheme.ui.activities.home.HomeActivity.Companion.ARTICLE_INTENT_ID
import com.commanderpepper.pheme.ui.theme.PhemeTheme
import com.commanderpepper.pheme.uistate.NewsItem
import com.commanderpepper.pheme.uistate.NewsItemExpanded
import com.commanderpepper.pheme.uistate.NewsItemUIState
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class ArticleActivity : ComponentActivity() {

    private val vm: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = this.intent
        val id = intent.getLongExtra(ARTICLE_INTENT_ID, 0L)
        vm.retrieveArticle(id)
        setContent {

            // If the phone is in landscape mode then the isExpandedScreen boolean is most likely true
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

            PhemeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val onBackPressedDispatcher =
                        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
                    DisplayArticle(isExpandedScreen = isExpandedScreen, articleViewModel = vm) {
                        onBackPressedDispatcher?.onBackPressed()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemPreview() {
    PhemeTheme {
        NewsItem(
            newsItemUIState = NewsItemUIState(
                publisher = "New York Times",
                author = "You, you wrote this. Congrats.",
                title = "This is an incredible article",
                thumbnail = "https://i.imgur.com/olisBgy.png",
                date = "January 1, 2022",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at tempus mi. Proin eget accumsan mauris. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc vitae eros pharetra, commodo magna a, luctus massa. Pellentesque imperdiet lectus a ante ornare, id tincidunt nibh congue. Sed finibus sapien id augue consequat, vitae molestie risus cursus. Aenean sed congue est. Duis quis eros turpis. Vestibulum non finibus massa. Fusce sem eros, bibendum eu orci in, volutpat feugiat mauris. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vestibulum ligula risus, ullamcorper id turpis dictum, convallis hendrerit quam. Aenean sapien lectus, faucibus in pharetra in, tincidunt ac mauris.\n" +
                        "\n" +
                        "Praesent in erat libero. Vestibulum sed arcu non elit accumsan maximus sed nec enim. Integer nisi turpis, condimentum nec tempor quis, ullamcorper vitae turpis. Fusce at vehicula nulla, et tincidunt arcu. Pellentesque non purus in massa posuere sagittis. Praesent sollicitudin aliquam risus vel placerat. Quisque interdum posuere nibh, et mattis tortor sagittis vestibulum. Nullam sit amet consequat turpis. Nulla at pharetra nisi. Nam suscipit purus neque, non gravida quam blandit sed. Sed pharetra erat eu pellentesque rutrum. Ut semper mauris quis feugiat volutpat. Vivamus pellentesque sapien arcu, viverra sollicitudin mi maximus ut. Nulla malesuada leo neque, ut bibendum risus tristique vitae. Proin blandit nulla id libero vestibulum, eu posuere purus bibendum.\n" +
                        "\n" +
                        "Integer laoreet lacinia dolor et ullamcorper. Sed et dictum turpis. Vivamus nulla nunc, sagittis auctor ipsum sed, pulvinar blandit eros. Phasellus auctor vitae leo eget facilisis. Phasellus mi mauris, consectetur a felis interdum, bibendum iaculis nulla. Nunc ut posuere felis. Praesent volutpat eros mauris, id ornare libero congue sit amet. Integer vitae risus at quam pharetra aliquet. Nam dapibus ac sem aliquet ullamcorper.\n" +
                        "\n" +
                        "Integer eu tellus blandit lacus convallis condimentum non vel magna. Quisque sed leo rhoncus, venenatis leo eu, laoreet tortor. Fusce ultricies, nisl nec ornare suscipit, augue risus ullamcorper neque, eget egestas nisi ex ut nibh. Nulla eget varius nulla. Aenean luctus ac elit non vulputate. Pellentesque congue a mi ac gravida. Morbi in quam id velit suscipit egestas et vitae enim. Praesent consectetur finibus purus a molestie. Sed mattis pellentesque tellus, et volutpat magna. In hac habitasse platea dictumst.\n" +
                        "\n" +
                        "Aliquam suscipit turpis nec quam convallis bibendum. Aenean laoreet nibh vitae sapien rhoncus, eget tristique tortor aliquam. Morbi scelerisque aliquam tincidunt. Morbi sem nisl, efficitur a lacus id, porta imperdiet nisl. Fusce id convallis lacus. Duis sit amet auctor odio, quis aliquet magna. Fusce a ullamcorper ante. Phasellus gravida mauris non lacinia luctus. Mauris fermentum efficitur ultricies. Aliquam hendrerit urna ligula, ut porta ex sodales non. In hac habitasse platea dictumst. Quisque consequat nunc ac urna congue, dictum feugiat ipsum tempus. Nulla eget nisi sit amet lectus interdum pellentesque accumsan facilisis nisl. Aliquam egestas mauris id fermentum tristique."
            )
        )
    }
}

@Preview(name = "News Item Expanded", device = Devices.NEXUS_7_2013, showBackground = true)
@Composable
fun NewsItemExpandedPreview(){
    PhemeTheme {
        NewsItemExpanded(
            newsItemUIState = NewsItemUIState(
                publisher = "New York Times",
                author = "You, you wrote this. Congrats.",
                title = "This is an incredible article",
                thumbnail = "https://i.imgur.com/olisBgy.png",
                date = "January 1, 2022",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at tempus mi. Proin eget accumsan mauris. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc vitae eros pharetra, commodo magna a, luctus massa. Pellentesque imperdiet lectus a ante ornare, id tincidunt nibh congue. Sed finibus sapien id augue consequat, vitae molestie risus cursus. Aenean sed congue est. Duis quis eros turpis. Vestibulum non finibus massa. Fusce sem eros, bibendum eu orci in, volutpat feugiat mauris. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vestibulum ligula risus, ullamcorper id turpis dictum, convallis hendrerit quam. Aenean sapien lectus, faucibus in pharetra in, tincidunt ac mauris.\n" +
                        "\n" +
                        "Praesent in erat libero. Vestibulum sed arcu non elit accumsan maximus sed nec enim. Integer nisi turpis, condimentum nec tempor quis, ullamcorper vitae turpis. Fusce at vehicula nulla, et tincidunt arcu. Pellentesque non purus in massa posuere sagittis. Praesent sollicitudin aliquam risus vel placerat. Quisque interdum posuere nibh, et mattis tortor sagittis vestibulum. Nullam sit amet consequat turpis. Nulla at pharetra nisi. Nam suscipit purus neque, non gravida quam blandit sed. Sed pharetra erat eu pellentesque rutrum. Ut semper mauris quis feugiat volutpat. Vivamus pellentesque sapien arcu, viverra sollicitudin mi maximus ut. Nulla malesuada leo neque, ut bibendum risus tristique vitae. Proin blandit nulla id libero vestibulum, eu posuere purus bibendum.\n" +
                        "\n" +
                        "Integer laoreet lacinia dolor et ullamcorper. Sed et dictum turpis. Vivamus nulla nunc, sagittis auctor ipsum sed, pulvinar blandit eros. Phasellus auctor vitae leo eget facilisis. Phasellus mi mauris, consectetur a felis interdum, bibendum iaculis nulla. Nunc ut posuere felis. Praesent volutpat eros mauris, id ornare libero congue sit amet. Integer vitae risus at quam pharetra aliquet. Nam dapibus ac sem aliquet ullamcorper.\n" +
                        "\n" +
                        "Integer eu tellus blandit lacus convallis condimentum non vel magna. Quisque sed leo rhoncus, venenatis leo eu, laoreet tortor. Fusce ultricies, nisl nec ornare suscipit, augue risus ullamcorper neque, eget egestas nisi ex ut nibh. Nulla eget varius nulla. Aenean luctus ac elit non vulputate. Pellentesque congue a mi ac gravida. Morbi in quam id velit suscipit egestas et vitae enim. Praesent consectetur finibus purus a molestie. Sed mattis pellentesque tellus, et volutpat magna. In hac habitasse platea dictumst.\n" +
                        "\n" +
                        "Aliquam suscipit turpis nec quam convallis bibendum. Aenean laoreet nibh vitae sapien rhoncus, eget tristique tortor aliquam. Morbi scelerisque aliquam tincidunt. Morbi sem nisl, efficitur a lacus id, porta imperdiet nisl. Fusce id convallis lacus. Duis sit amet auctor odio, quis aliquet magna. Fusce a ullamcorper ante. Phasellus gravida mauris non lacinia luctus. Mauris fermentum efficitur ultricies. Aliquam hendrerit urna ligula, ut porta ex sodales non. In hac habitasse platea dictumst. Quisque consequat nunc ac urna congue, dictum feugiat ipsum tempus. Nulla eget nisi sit amet lectus interdum pellentesque accumsan facilisis nisl. Aliquam egestas mauris id fermentum tristique."
            )
        )
    }
}