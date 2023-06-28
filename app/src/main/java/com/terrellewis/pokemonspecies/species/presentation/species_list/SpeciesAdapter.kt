package com.terrellewis.pokemonspecies.species.presentation.species_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.terrellewis.pokemonspecies.R
import com.terrellewis.pokemonspecies.core.utils.getPokemonSpeciesImageUrl
import com.terrellewis.pokemonspecies.core.utils.loadUrl
import com.terrellewis.pokemonspecies.databinding.ListItemSpeciesBinding
import com.terrellewis.pokemonspecies.species.domain.model.Species


class SpeciesAdapter(
    private val onSpeciesClickListener: (Int) -> Unit
) : PagingDataAdapter<Species, SpeciesAdapter.ViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ListItemSpeciesBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding) {
            Log.d("Adapter", "Clicked on position $it")
            if (it != RecyclerView.NO_POSITION) {
                val species = getItem(it)
                if (species != null) {
                    onSpeciesClickListener.invoke(species.id)
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("Adapter", "Binding item: $item")
        if (item != null) {
            holder.bind(item)
        }
    }

    class ViewHolder(
        private val binding: ListItemSpeciesBinding,
        onSpeciesClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onSpeciesClicked(bindingAdapterPosition)
            }
        }

        fun bind(item: Species) {
            binding.speciesNameTextview.text = item.name
            binding.speciesThumbnailImageview.loadUrl(getPokemonSpeciesImageUrl(item.id))
        }
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Species>() {
    override fun areItemsTheSame(oldItem: Species, newItem: Species): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Species, newItem: Species): Boolean =
        oldItem == newItem
}