package com.commanderpepper.pheme.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.data.repository.repos.NewsRepository
import com.commanderpepper.pheme.data.repository.Status
import com.commanderpepper.pheme.ui.screens.articlelist.ArticleListUIState
import com.commanderpepper.pheme.ui.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.ui.uistate.checkArticle
import com.commanderpepper.pheme.domain.usecase.CreateNewsPreviewItemUseCase
import com.commanderpepper.pheme.util.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val createNewsPreviewItemUseCase: CreateNewsPreviewItemUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _categoryFlow = MutableStateFlow(Category.NEWS)
    val categoryFlow : StateFlow<Category> = _categoryFlow

    private val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow: StateFlow<String> = _searchQueryFlow.asStateFlow()

    private val _homeTopAppBarCategoryTextFlow = MutableStateFlow((Category.NEWS.category).capitalize(Locale.US))
    val homeTopAppBarCategoryTextFlow: StateFlow<String> = _homeTopAppBarCategoryTextFlow

    // This is the articles to search against, this list allows the user to search without affecting the list of data to display
    private val fetchedArticlesToSearchAgainst = mutableListOf<NewsPreviewItemUIState>()

    val articleUIListStateFlow = combine(categoryFlow, _searchQueryFlow) { category, searchQuery ->
            if (searchQuery.isNotBlank()) {
                flow {
                    emit(ArticleListUIState.Loading)
                    val filteredArticles = fetchedArticlesToSearchAgainst.filter { it.checkArticle(searchQuery) }
                    if (filteredArticles.isEmpty()) {
                        emit(ArticleListUIState.Error(stringProvider.getString(R.string.articles_searched_error_message)))
                    } else {
                        emit(ArticleListUIState.Success(filteredArticles))
                    }
                }
            } else {
                newsRepository.fetchArticles(category).map { status ->
                    when (status) {
                        is Status.InProgress -> ArticleListUIState.Loading
                        is Status.Failure -> ArticleListUIState.Error(status.message)
                        is Status.Success -> {
                            val newsItemUiList = status.data.map { createNewsPreviewItemUseCase(it) }
                            fetchedArticlesToSearchAgainst.clear()
                            fetchedArticlesToSearchAgainst.addAll(newsItemUiList)
                            ArticleListUIState.Success(newsItemUiList)
                        }
                    }
                }
            }
        }
            .flattenConcat()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
                initialValue = ArticleListUIState.Loading
            )

    /**
     * Clear the search
     */
    fun clearSearch() {
        viewModelScope.launch {
            _searchQueryFlow.emit("")
        }
    }

    /**
     * Search articles using the query
     * @param query - query to use to filter articles
     */
    fun searchArticles(query: String) {
        viewModelScope.launch {
            _searchQueryFlow.emit(query)
        }
    }

    fun updateCategory(category: Category){
        viewModelScope.launch {
            _homeTopAppBarCategoryTextFlow.emit(category.category.capitalize(Locale.US))
            _categoryFlow.emit(category)
        }
    }
}