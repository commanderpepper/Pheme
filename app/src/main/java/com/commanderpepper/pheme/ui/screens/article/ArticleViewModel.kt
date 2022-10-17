package com.commanderpepper.pheme.ui.screens.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.Status
import com.commanderpepper.pheme.ui.screens.article.ArticleUIState
import com.commanderpepper.pheme.usecase.ConvertArticleEntityToNewsItemUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val convertArticleEntityToNewsItemUIState: ConvertArticleEntityToNewsItemUIState
) : ViewModel() {

    private val _articleUIState = MutableStateFlow(ArticleUIState())
    val articleUIState: StateFlow<ArticleUIState> = _articleUIState

    private var viewModelJob: Job? = null

    fun retrieveArticle(id: Long) {
        viewModelJob?.cancel()

        viewModelJob = viewModelScope.launch {
            repository.fetchSingleArticle(id).catch {
                _articleUIState.emit(_articleUIState.value.copy(isError = true))
            }.collect { status ->
                when (status) {
                    is Status.Failure -> _articleUIState.emit(_articleUIState.value.copy(isError = true))
                    is Status.InProgress -> _articleUIState.emit(
                        _articleUIState.value.copy(
                            isLoading = true
                        )
                    )
                    is Status.Success -> _articleUIState.emit(
                        ArticleUIState(
                            isLoading = false,
                            isError = false,
                            newsItemUIState = convertArticleEntityToNewsItemUIState(status.data)
                        )
                    )
                }
            }
        }
    }
}