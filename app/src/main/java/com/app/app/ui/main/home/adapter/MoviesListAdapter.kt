package com.app.app.ui.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.app.app.databinding.MovieItemBinding
import com.core.base.BaseViewHolder
import com.core.data.model.login.MoviesResponse

class MoviesListAdapter(private val clickListener: ClickListener) :
    ListAdapter<MoviesResponse.Result, BaseViewHolder>(
        USER_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    inner class ViewHolder(var binding: MovieItemBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(position: Int) {
            binding.run {
                model = getItem(position)
                listener = clickListener
            }
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<MoviesResponse.Result>() {
            override fun areItemsTheSame(
                oldItem: MoviesResponse.Result,
                newItem: MoviesResponse.Result
            ): Boolean =
                newItem.id == oldItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: MoviesResponse.Result,
                newItem: MoviesResponse.Result
            ): Boolean =
                newItem == oldItem
        }
    }

    interface ClickListener {
        fun onItemClick(model: MoviesResponse.Result)
    }
}
