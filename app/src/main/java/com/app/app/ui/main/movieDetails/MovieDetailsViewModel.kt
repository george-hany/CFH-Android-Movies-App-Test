package com.app.app.ui.main.movieDetails

import androidx.lifecycle.viewModelScope
import com.app.app.ui.main.home.MovieIntent
import com.core.base.BaseViewModel
import com.core.data.model.login.MovieDetailsResponse
import com.core.data.network.model.ResponseState
import com.core.data.repos.MovieDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(val repo: MovieDetailsRepo) : BaseViewModel<MovieDetailsRepo>(repo) {
    private val _movieDetailsResponseFlow = MutableStateFlow<MovieDetailsResponse?>(null)
    val movieDetailsResponseFlow: StateFlow<MovieDetailsResponse?> = _movieDetailsResponseFlow
    fun handleIntent(intent: MovieIntent) {
        viewModelScope.launch {
            when (intent) {
                is MovieIntent.LoadMovieDetails -> fetchMovieDetails(intent.movieId)
                else -> {}
            }
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repo.requestMoviesDetails(movieId).collect {
                isLoading.value = it is ResponseState.Loading
                when (it) {
                    is ResponseState.Success -> _movieDetailsResponseFlow.value = it.data
                    is ResponseState.Error -> {
                        message.value = it.message
                    }

                    is ResponseState.Loading -> isLoading.value = true
                }
            }
        }
    }
}
