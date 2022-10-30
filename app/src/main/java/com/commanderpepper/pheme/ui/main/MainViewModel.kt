package com.commanderpepper.pheme.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.Category
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.Status
import com.commanderpepper.pheme.uistate.CategoryUIState
import com.commanderpepper.pheme.ui.screens.articlelist.ArticleListUIState
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.usecase.CreateNewsPreviewItemUseCase
import com.commanderpepper.pheme.util.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val createNewsPreviewItemUseCase: CreateNewsPreviewItemUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _categoryUIStateFlow = MutableStateFlow(CategoryUIState())
    val categoryUIState: StateFlow<CategoryUIState> = _categoryUIStateFlow

    private val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow: StateFlow<String> = _searchQueryFlow.asStateFlow()

    // This is the articles to search against, this list allows the user to search without affecting the list of data to display
    private val fetchedArticlesToSearchAgainst = mutableListOf<NewsPreviewItemUIState>()

    val articleUIListStateFlow =
        _categoryUIStateFlow.combine(_searchQueryFlow) { categoryUIState, searchQuery ->
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
                newsRepository.fetchArticles(categoryUIState.currentCategory).map { status ->
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
     * User event for the view model to handle
     * @param category Category that was clicked on
     */
    fun categoryClicked(category: Category) {
        viewModelScope.launch {
            clearSearch()
            val currentCategoryValue = _categoryUIStateFlow.value.currentCategory
            if (currentCategoryValue != category) {
                _categoryUIStateFlow.emit(
                    _categoryUIStateFlow.value.copy(
                        currentCategory = category
                    )
                )
            }
        }
    }

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

    /**
     * Check article for title, author and publisher
     * @param searchQuery - text to query the NewsPreviewItemUIState against
     */
    private fun NewsPreviewItemUIState.checkArticle(searchQuery: String): Boolean {
        return this.title.contains(searchQuery, ignoreCase = true) ||
                this.author.contains(searchQuery, ignoreCase = true) ||
                this.publisher.contains(searchQuery, ignoreCase = true)
    }
}