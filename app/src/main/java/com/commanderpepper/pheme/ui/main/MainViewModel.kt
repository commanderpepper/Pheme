package com.commanderpepper.pheme.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.data.Category
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.Status
import com.commanderpepper.pheme.ui.homebottombar.CategoryUIState
import com.commanderpepper.pheme.ui.screens.articlelist.ArticleListUIState
import com.commanderpepper.pheme.usecase.CreateNewsPreviewItemUseCase
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val createNewsPreviewItemUseCase: CreateNewsPreviewItemUseCase
) : ViewModel(){
    
    private val _articleUIListStateFlow = MutableStateFlow(ArticleListUIState())
    val articleUIListStateFlow: StateFlow<ArticleListUIState> = _articleUIListStateFlow

    private val _categoryUIStateFlow = MutableStateFlow(CategoryUIState())
    val categoryUIState: StateFlow<CategoryUIState> = _categoryUIStateFlow

    private var viewModelJob: Job? = null

    /**
     * User event for the view model to handle
     * @param category Category that was clicked on
     */
    fun categoryClicked(category: Category){
        viewModelScope.launch {
            val currentCategoryValue = _categoryUIStateFlow.value.currentCategory
            // If the category clicked is not the same as the current category then retrieve articles
            if(currentCategoryValue != category){
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
    fun loadArticles(){
        val category = _categoryUIStateFlow.value.currentCategory
        fetchArticles(category)
    }

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
                    _articleUIListStateFlow.value.copy(isLoading = false, isError = true)
                )
            }.collect { status ->
                when (status) {
                    is Status.InProgress ->
                        _articleUIListStateFlow.emit(
                            _articleUIListStateFlow.value.copy(
                                isLoading = true,
                                isError = false
                            )
                        )
                    is Status.Success -> _articleUIListStateFlow.emit(
                        _articleUIListStateFlow.value.copy(
                            isLoading = false,
                            isError = false,
                            newsPreviewList = status.data.map { createNewsPreviewItemUseCase(it) }),
                    )
                    is Status.Failure -> _articleUIListStateFlow.emit(
                        _articleUIListStateFlow.value.copy(isLoading = false, isError = true)
                    )
                }
            }
        }
    }
}