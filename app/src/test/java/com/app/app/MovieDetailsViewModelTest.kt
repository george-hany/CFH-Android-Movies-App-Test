package com.app.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.app.ui.main.home.MovieIntent
import com.app.app.ui.main.movieDetails.MovieDetailsViewModel
import com.core.data.model.login.MovieDetailsResponse
import com.core.data.network.model.ResponseState
import com.core.data.repos.MovieDetailsRepo
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: MovieDetailsViewModel
    private val repo: MovieDetailsRepo = mock()

    @Before
    fun setup() {
        viewModel = MovieDetailsViewModel(repo)
    }

    @Test
    fun `when LoadMovieDetails intent is passed, movie details is fetched`() = runTest {
        // Given
        val movieId = 111
        val movieDetails = MovieDetailsResponse(
            adult = false,
            backdropPath = "path",
            id = movieId,
            originalLanguage = "en",
            originalTitle = "originalTitle",
            overview = "overview",
            popularity = 2.5,
            posterPath = "posterPath",
            releaseDate = "releaseDate",
            title = "title",
            video = true,
            voteAverage = 5.5,
            voteCount = 13
        )

        whenever(repo.requestMoviesDetails(movieId)).thenReturn(
            MutableStateFlow(
                ResponseState.Success(
                    movieDetails
                )
            )
        )

        // When
        viewModel.handleIntent(MovieIntent.LoadMovieDetails(movieId))
        advanceUntilIdle()
        // Then
        assertEquals(movieDetails, viewModel.movieDetailsResponseFlow.value)
    }
}