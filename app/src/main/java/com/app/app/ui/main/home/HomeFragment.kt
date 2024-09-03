package com.app.app.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.app.BR
import com.app.app.R
import com.app.app.databinding.FragmentHomeBinding
import com.app.app.ui.main.home.adapter.MoviesListAdapter
import com.app.app.ui.main.movieDetails.MovieDetailsFragment
import com.core.base.BaseFragment
import com.core.data.model.login.MoviesResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    val homeViewModel: HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        loadMovies()
        collectMoviesResponseFlow()
        swipeRefreshListener()
    }

    private fun initAdapter() {
        viewDataBinding.adapter = MoviesListAdapter(object : MoviesListAdapter.ClickListener {
            override fun onItemClick(model: MoviesResponse.Result) {
                navigation.navigate(R.id.action_homeFragment_to_movieDetailsFragment, Bundle().apply {
                    putInt(MovieDetailsFragment.MOVIE_ID_EXTRA_PARAM, model.id ?: 0)
                })
            }
        })
    }

    private fun swipeRefreshListener() {
        viewDataBinding.swipeRefreshLayout.setOnRefreshListener {
            loadMovies()
            viewDataBinding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun collectMoviesResponseFlow() {
        lifecycleScope.launch {
            homeViewModel.moviesResponseFlow.collect { moviesResponse ->
                moviesResponse?.results?.let {
                    viewDataBinding.swipeRefreshLayout.isRefreshing = false
                    renderMoviesList(it)
                }
            }
        }
    }

    private fun renderMoviesList(moviesList: List<MoviesResponse.Result?>) {
        viewDataBinding.adapter?.submitList(moviesList)
    }

    private fun loadMovies() {
        homeViewModel.handleIntent(MovieIntent.LoadMovies)
    }

    override fun bindingVariable(): Int = BR.viewModel

    override fun layoutId(): Int = R.layout.fragment_home

    override fun getViewModel(): HomeViewModel = homeViewModel
}
