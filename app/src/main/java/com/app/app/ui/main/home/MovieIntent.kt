package com.app.app.ui.main.home

sealed class MovieIntent {
    data object LoadMovies : MovieIntent()
    data class LoadMovieDetails(val movieId: Int) : MovieIntent()
}