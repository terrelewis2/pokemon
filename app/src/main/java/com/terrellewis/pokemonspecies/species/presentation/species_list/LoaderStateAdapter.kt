package com.terrellewis.pokemonspecies.species.presentation.species_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.terrellewis.pokemonspecies.core.utils.ApiErrorUtils.getErrorMessage
import com.terrellewis.pokemonspecies.databinding.ItemFooterLoaderBinding

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ItemFooterLoaderBinding.inflate(inflater, parent, false)
        return LoaderViewHolder(binding, retry)
    }

    /**
     * view holder class for footer loader and error state handling
     */
    class LoaderViewHolder(
        private val binding: ItemFooterLoaderBinding,
        retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                loadingIndicator.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMessageTextview.isVisible = loadState is LoadState.Error
                if (loadState is LoadState.Error) {
                    val message = getErrorMessage(loadState.error)
                    errorMessageTextview.text = message
                }
            }
        }
    }
}