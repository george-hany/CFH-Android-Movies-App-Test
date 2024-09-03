package com.app.app.ui.main.home

import androidx.lifecycle.viewModelScope
import com.core.base.BaseViewModel
import com.core.data.model.login.MoviesResponse
import com.core.data.network.model.ResponseState
import com.core.data.repos.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(val repo: HomeRepo) : BaseViewModel<HomeRepo>(repo) {
    private val _moviesResponseFlow = MutableStateFlow<MoviesResponse?>(null)
    val moviesResponseFlow: StateFlow<MoviesResponse?> = _moviesResponseFlow
    fun handleIntent(intent: MovieIntent) {
        viewModelScope.launch {
            when (intent) {
                is MovieIntent.LoadMovies -> fetchMovies()
                else -> {}
            }
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            repo.requestMoviesList().collect {
                isLoading.value = it is ResponseState.Loading
                when (it) {
                    is ResponseState.Success -> _moviesResponseFlow.value = it.data
                    is ResponseState.Error -> {
                        message.value = it.message
                    }

                    is ResponseState.Loading -> isLoading.value = true
                }
            }
        }
    }
}
