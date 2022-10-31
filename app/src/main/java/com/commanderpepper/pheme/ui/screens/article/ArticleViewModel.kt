package com.commanderpepper.pheme.ui.screens.article

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commanderpepper.pheme.data.repository.repos.NewsRepository
import com.commanderpepper.pheme.data.repository.Status
import com.commanderpepper.pheme.domain.usecase.ConvertArticleEntityToNewsItemUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val convertArticleEntityToNewsItemUIState: ConvertArticleEntityToNewsItemUIState
) : ViewModel() {

    private val _articleUIState = MutableStateFlow(ArticleUIState())
    val articleUIState: StateFlow<ArticleUIState> = _articleUIState.asStateFlow()

    private var viewModelJob: Job? = null

    /**
     * Retrieve an article
     * @param id ID to be used to retrieve article from
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun retrieveArticle(id: Long) {
        viewModelJob?.cancel()

        viewModelJob = viewModelScope.launch {
            repository.fetchSingleArticle(id).catch {
                _articleUIState.emit(_articleUIState.value.copy(isError = true))
            }.collect { status ->
                when (status) {
                    is Status.Failure -> _articleUIState.emit(
                        ArticleUIState(
                            isLoading = false,
                            isError = true,
                            isSuccess = false,
                            newsItemUIState = null
                        )
                    )
                    is Status.InProgress -> _articleUIState.emit(
                        ArticleUIState(
                            isLoading = true,
                            isError = false,
                            isSuccess = false,
                            newsItemUIState = null
                        )
                    )
                    is Status.Success -> _articleUIState.emit(
                        ArticleUIState(
                            isLoading = false,
                            isError = false,
                            isSuccess = true,
                            newsItemUIState = convertArticleEntityToNewsItemUIState(status.data)
                        )
                    )
                }
            }
        }
    }
}