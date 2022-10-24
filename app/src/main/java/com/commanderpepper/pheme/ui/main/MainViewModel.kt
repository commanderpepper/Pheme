package com.commanderpepper.pheme.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.data.Category
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.Status
import com.commanderpepper.pheme.ui.homebottombar.CategoryUIState
import com.commanderpepper.pheme.ui.screens.articlelist.ArticleListUIState
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.usecase.CreateNewsPreviewItemUseCase
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val createNewsPreviewItemUseCase: CreateNewsPreviewItemUseCase
) : ViewModel() {

    private val _articleUIListStateFlow =
        MutableStateFlow<ArticleListUIState>(ArticleListUIState.Articles())
    val articleUIListStateFlow: StateFlow<ArticleListUIState> = _articleUIListStateFlow

    private val _categoryUIStateFlow = MutableStateFlow(CategoryUIState())
    val categoryUIState: StateFlow<CategoryUIState> = _categoryUIStateFlow

    private val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow: StateFlow<String> = _searchQueryFlow.asStateFlow()

    // This is the articles to search against, this list allows the user to search without affecting the list of data to display
    private val fetchedArticlesToSearchAgainst = mutableListOf<NewsPreviewItemUIState>()

    private var viewModelJob: Job? = null

    /**
     * User event for the view model to handle
     * @param category Category that was clicked on
     */
    fun categoryClicked(category: Category) {
        viewModelScope.launch {
            clearSearch()

            val currentCategoryValue = _categoryUIStateFlow.value.currentCategory
            // If the category clicked is not the same as the current category then retrieve articles
            if (currentCategoryValue != category) {
                _categoryUIStateFlow.emit(
                    _categoryUIStateFlow.value.copy(
                        currentCategory = category
                    )
                )
                val categoryValue = _categoryUIStateFlow.value.currentCategory
                fetchArticles(categoryValue)
            }
        }
    }

    /**
     * Load articles from a repo
     */
    private fun loadArticles() {
        val category = _categoryUIStateFlow.value.currentCategory
        fetchArticles(category)
    }

    /**
     * Load the data according to the current UI state, either fetching articles or searching for articles
     */
    fun loadData() {
        if (_articleUIListStateFlow.value is ArticleListUIState.Articles) {
            loadArticles()
        } else {
            searchArticles(_searchQueryFlow.value)
        }
    }

    /**
     * Clear the search
     */
    fun clearSearch() {
        viewModelScope.launch {
            _searchQueryFlow.emit("")
            if (_articleUIListStateFlow.value is ArticleListUIState.Searching) {
                fetchArticles(_categoryUIStateFlow.value.currentCategory)
            }
        }
    }

    /**
     * Search articles using the query
     * @param query - query to use to filter articles
     */
    fun searchArticles(query: String) {
        viewModelScope.launch {
            _searchQueryFlow.emit(query)
            val searchQuery = _searchQueryFlow.value

            _articleUIListStateFlow.emit(
                ArticleListUIState.Searching(
                    isSuccessful = false,
                    isLoading = true,
                    isEmpty = false,
                    newsPreviewSearchList = emptyList()
                )
            )

            if (searchQuery.isNotBlank()) {
                val articles = fetchedArticlesToSearchAgainst
                val filteredArticles = articles.filter {
                    it.checkArticle(searchQuery)
                }
                if (filteredArticles.isNotEmpty()) {
                    _articleUIListStateFlow.emit(
                        ArticleListUIState.Searching(
                            isSuccessful = true,
                            isEmpty = false,
                            isLoading = false,
                            newsPreviewSearchList = filteredArticles
                        )
                    )
                } else {
                    _articleUIListStateFlow.emit(
                        ArticleListUIState.Searching(
                            isSuccessful = false,
                            isEmpty = true,
                            isLoading = false,
                            newsPreviewSearchList = emptyList()
                        )
                    )
                }
            }
        }
    }

    /**
     * Fetch articles from the repository
     * @param category - category to be used to fetch articles from
     */
    private fun fetchArticles(category: Category = Category.NEWS) {
        // Cancel the current job if one exists
        viewModelJob?.cancel()

        // Select the appropriate method to create the flow with
        val returnFlow: (Category) -> Flow<Status<out List<ArticleInBetween>>> =
            newsRepository::fetchArticles

        // Create a Job and assign it to the ViewModel Job. This Job will make a call to the Repository to gather the articles.
        viewModelJob = viewModelScope.launch {
            returnFlow(category).catch {
                _articleUIListStateFlow.emit(
                    ArticleListUIState.Articles(
                        isLoading = false,
                        isError = true,
                        isSuccessful = false,
                        newsPreviewList = listOf()
                    )
                )
            }.collect { status ->
                when (status) {
                    is Status.InProgress -> _articleUIListStateFlow.emit(
                        ArticleListUIState.Articles(
                            isLoading = true,
                            isError = false,
                            isSuccessful = false,
                            newsPreviewList = listOf()
                        )
                    )
                    is Status.Success -> {
                        fetchedArticlesToSearchAgainst.clear()
                        fetchedArticlesToSearchAgainst.addAll(status.data.map {
                            createNewsPreviewItemUseCase(
                                it
                            )
                        })

                        _articleUIListStateFlow.emit(
                            ArticleListUIState.Articles(
                                isLoading = false,
                                isError = false,
                                isSuccessful = true,
                                newsPreviewList = fetchedArticlesToSearchAgainst
                            )
                        )
                    }
                    is Status.Failure -> _articleUIListStateFlow.emit(
                        ArticleListUIState.Articles(
                            isLoading = false,
                            isError = true,
                            isSuccessful = false,
                            newsPreviewList = emptyList()
                        )
                    )
                }
            }
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