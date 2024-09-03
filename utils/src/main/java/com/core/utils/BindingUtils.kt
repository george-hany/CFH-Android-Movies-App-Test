package com.core.utils

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import timber.log.Timber

object BindingUtils {
    @BindingAdapter("app:error")
    @JvmStatic
    fun bindError(
        view: EditText,
        error: String?,
    ) {
        if (error != null) {
            Timber.tag("Error").e(error)
            view.error = error
            view.requestFocus()
        }
    }

    @BindingAdapter("app:image")
    @JvmStatic
    fun bindImage(
        view: ImageView,
        url: String?,
    ) {
        val link = "https://image.tmdb.org/t/p/original$url"
        if (url != null) {
            Glide.with(view.context).load(link).into(view)
        }
    }

    @BindingAdapter("app:bindReleaseDate")
    @JvmStatic
    fun bindReleaseDate(
        view: TextView,
        date: String?,
    ) {
        if (date != null)
            view.text = "Release Date: $date"
    }

    @BindingAdapter("app:bindRuntime")
    @JvmStatic
    fun bindRuntime(
        view: TextView,
        runtime: Int?,
    ) {
        if (runtime != null)
            view.text = "Runtime: $runtime minutes"
    }

    @BindingAdapter("app:bindVoteAverage")
    @JvmStatic
    fun bindVoteAverage(
        view: TextView,
        vote: Double?,
    ) {
        if (vote != null)
            view.text = "Rating: $vote"
    }

    @BindingAdapter("app:bindVoteCount")
    @JvmStatic
    fun bindVoteCount(
        view: TextView,
        vote: Int?,
    ) {
        if (vote != null)
            view.text = "Votes: $vote"
    }

    @BindingAdapter("app:bindGenres")
    @JvmStatic
    fun bindGenres(
        view: TextView,
        genres: String?,
    ) {
        if (genres != null)
            view.text = "Genres: $genres"
    }

    @BindingAdapter("app:adapter")
    @JvmStatic
    fun bindAdapter(
        view: RecyclerView,
        adapter: Any?,
    ) {
        if (adapter != null) {
            view.adapter = adapter as RecyclerView.Adapter<*>?
        }
    }
}
