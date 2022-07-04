package com.commanderpepper.pheme.ui.activities.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.usecase.CreateNewsPreviewItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val createNewsPreviewItemUseCase: CreateNewsPreviewItemUseCase) : ViewModel() {

    private val _flow = MutableStateFlow<List<NewsPreviewItemUIState>>(emptyList())
    val flow : StateFlow<List<NewsPreviewItemUIState>> = _flow

    init {
        viewModelScope.launch {
            _flow.value = newsRepository.fetchNewsWithCategory("America").map { createNewsPreviewItemUseCase.invoke(it) }
        }
    }
}