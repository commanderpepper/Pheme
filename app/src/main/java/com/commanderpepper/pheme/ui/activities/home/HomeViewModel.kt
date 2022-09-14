package com.commanderpepper.pheme.ui.activities.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.Status
import com.commanderpepper.pheme.repository.local.Category
import com.commanderpepper.pheme.usecase.CreateNewsPreviewItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val createNewsPreviewItemUseCase: CreateNewsPreviewItemUseCase) : ViewModel() {

    private val _homeUIStateFlow = MutableStateFlow(HomeUIState())
    val homeUIState : StateFlow<HomeUIState> = _homeUIStateFlow

    private var viewModelJob : Job? = null

    fun fetchNews(category: String = "America"){
        // Cancel the current job if one exists
        viewModelJob?.cancel()

        // Create a Job and assign it to the ViewModel Job. This Job will make a call to the Repository to gather the articles.
        viewModelJob = viewModelScope.launch {
            newsRepository.fetchNewsWithCountry(Category.NEWS).collect { status ->
                when(status){
                    is Status.InProgress -> _homeUIStateFlow.value = _homeUIStateFlow.value.copy(isLoading = true, isError = false)
                    is Status.Success -> _homeUIStateFlow.value = _homeUIStateFlow.value.copy(isLoading = false, isError = false, newsPreviewList = status.data.map { createNewsPreviewItemUseCase(it) })
                    is Status.Failure -> _homeUIStateFlow.value = _homeUIStateFlow.value.copy(isLoading = false, isError = true)
                }
            }
        }
    }
}