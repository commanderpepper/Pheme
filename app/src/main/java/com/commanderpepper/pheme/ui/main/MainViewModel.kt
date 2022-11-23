package com.commanderpepper.pheme.ui.main

import androidx.compose.ui.text.capitalize
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.data.repository.repos.NewsRepository
import com.commanderpepper.pheme.data.repository.Status
import com.commanderpepper.pheme.data.retrofit.model.getCategory
import com.commanderpepper.pheme.ui.uistate.CategoryUIState
import com.commanderpepper.pheme.ui.screens.articlelist.ArticleListUIState
import com.commanderpepper.pheme.ui.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.ui.uistate.checkArticle
import com.commanderpepper.pheme.domain.usecase.CreateNewsPreviewItemUseCase
import com.commanderpepper.pheme.util.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val createNewsPreviewItemUseCase: CreateNewsPreviewItemUseCase,
    private val stringProvider: StringProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val category: String? = savedStateHandle["category"]

    private val _categoryUIStateFlow = MutableStateFlow(CategoryUIState())
    val categoryUIState: StateFlow<CategoryUIState> = _categoryUIStateFlow

    private val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow: StateFlow<String> = _searchQueryFlow.asStateFlow()

    val homeTopAppBarCategoryTextFlow = _categoryUIStateFlow.map {
        it.currentCategory.category.capitalize()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = Category.NEWS.category
    )

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
                val localCategory = category.getCategory()
                newsRepository.fetchArticles(localCategory).map { status ->
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
}