package com.app.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.app.ui.main.home.HomeViewModel
import com.app.app.ui.main.home.MovieIntent
import com.core.data.model.login.MoviesResponse
import com.core.data.network.model.ResponseState
import com.core.data.repos.HomeRepo
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
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: HomeViewModel
    private val repo: HomeRepo = mock()

    @Before
    fun setup() {
        viewModel = HomeViewModel(repo)
    }

    @Test
    fun `when LoadMovies intent is passed, movies list is fetched`() = runTest {
        // Given
        val moviesResponse = MoviesResponse(
            page = 1,
            results = listOf(
                MoviesResponse.Result(
                    genreIds = null,
                    adult = false,
                    backdropPath = "path",
                    id = 100,
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
            ),
            totalPages = 10,
            totalResults = 3
        )
        whenever(repo.requestMoviesList()).thenReturn(
            MutableStateFlow(
                ResponseState.Success(
                    moviesResponse
                )
            )
        )

        // When
        viewModel.handleIntent(MovieIntent.LoadMovies)
        advanceUntilIdle()
        // Then
        assertEquals(moviesResponse, viewModel.moviesResponseFlow.value)
    }
}