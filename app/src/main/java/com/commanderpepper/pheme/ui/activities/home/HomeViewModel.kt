package com.commanderpepper.pheme.ui.activities.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.Status
import com.commanderpepper.pheme.repository.local.Category
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
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val createNewsPreviewItemUseCase: CreateNewsPreviewItemUseCase
) : ViewModel() {

    private val _homeUIStateFlow = MutableStateFlow(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> = _homeUIStateFlow

    private var viewModelJob: Job? = null

    fun categoryClicked(category: Category){
        viewModelScope.launch {
            val currentCategoryValue = _homeUIStateFlow.value.currentCategory
            // If the category clicked is not the same as the current category then retrieve articles
            if(currentCategoryValue != category){
                _homeUIStateFlow.emit(
                    _homeUIStateFlow.value.copy(
                        currentCategory = category
                    )
                )
                val categoryValue = _homeUIStateFlow.value.currentCategory
                fetchArticles(categoryValue)
            }
        }
    }

    fun loadArticles(){
        val category = _homeUIStateFlow.value.currentCategory
        fetchArticles(category)
    }

    private fun fetchArticles(category: Category = Category.NEWS) {
        // Cancel the current job if one exists
        viewModelJob?.cancel()

        // Select the appropriate method to create the flow with
        val returnFlow: (Category) -> Flow<Status<out List<ArticleInBetween>>> =
            if (category == Category.NEWS) {
                newsRepository::fetchNewsWithCountry
            } else newsRepository::fetchNewsWithCategory

        // Create a Job and assign it to the ViewModel Job. This Job will make a call to the Repository to gather the articles.
        viewModelJob = viewModelScope.launch {
            returnFlow(category).catch {
                _homeUIStateFlow.emit(
                    _homeUIStateFlow.value.copy(isLoading = false, isError = true)
                )
            }.collect { status ->
                when (status) {
                    is Status.InProgress ->
                        _homeUIStateFlow.emit(
                            _homeUIStateFlow.value.copy(
                                isLoading = true,
                                isError = false
                            )
                        )
                    is Status.Success -> _homeUIStateFlow.emit(
                        _homeUIStateFlow.value.copy(
                            isLoading = false,
                            isError = false,
                            newsPreviewList = status.data.map { createNewsPreviewItemUseCase(it) }),
                    )
                    is Status.Failure -> _homeUIStateFlow.emit(
                        _homeUIStateFlow.value.copy(isLoading = false, isError = true)
                    )
                }
            }
        }
    }
}