package com.app.app.ui.main.movieDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.app.BR
import com.app.app.R
import com.app.app.databinding.FragmentMovieDetailsBinding
import com.app.app.ui.main.home.MovieIntent
import com.core.base.BaseFragment
import com.core.utils.BindingUtils.bindGenres
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {
    val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        collectMovieDetails()
    }

    private fun collectMovieDetails() {
        lifecycleScope.launch {
            movieDetailsViewModel.movieDetailsResponseFlow.collect { movieDetailsResponse ->
                movieDetailsResponse?.let { details ->
                    bindGenres(
                        viewDataBinding.genresText,
                        details.genres?.map { it?.name }?.joinToString()
                    )
                }
            }
        }
    }

    private fun getArgs() {
        val movieId = arguments?.getInt(MOVIE_ID_EXTRA_PARAM)
        movieId?.let {
            movieDetailsViewModel.handleIntent(MovieIntent.LoadMovieDetails(it))
        }
    }

    override fun bindingVariable(): Int = BR.viewModel

    override fun layoutId(): Int = R.layout.fragment_movie_details

    override fun getViewModel(): MovieDetailsViewModel = movieDetailsViewModel

    companion object {
        const val MOVIE_ID_EXTRA_PARAM = "MOVIE_ID_EXTRA_PARAM"
    }
}