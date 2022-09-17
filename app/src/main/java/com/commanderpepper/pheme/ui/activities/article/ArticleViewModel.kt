package com.commanderpepper.pheme.ui.activities.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.Status
import com.commanderpepper.pheme.usecase.ConvertArticleEntityToNewsItemUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val convertArticleEntityToNewsItemUIState: ConvertArticleEntityToNewsItemUIState
): ViewModel() {

    private val _articleUIState = MutableStateFlow(ArticleUIState())
    val articleUIState : StateFlow<ArticleUIState> = _articleUIState

    private var viewModelJob : Job? = null

    fun retrieveArticle(id: Long){
        viewModelJob?.cancel()

        viewModelJob = viewModelScope.launch {
            repository.fetchSingleArticle(id).map { status ->
                when(status){
                    is Status.Failure -> _articleUIState.value = _articleUIState.value.copy(isError = true)
                    is Status.InProgress -> _articleUIState.value = _articleUIState.value.copy(isLoading = true)
                    is Status.Success -> _articleUIState.value = ArticleUIState(isLoading = false, isError = false, newsItemUIState = convertArticleEntityToNewsItemUIState(status.data))
                }
            }
        }
    }
}