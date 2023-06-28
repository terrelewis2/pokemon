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
import com.terrellewis.pokemonspecies.species.domain.model.Species


class SpeciesAdapter(
    private val onSpeciesClickListener: (Int) -> Unit
) : PagingDataAdapter<Species, SpeciesAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("Adapter", "Creating view holder")
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_species, parent, false)
        val viewHolder = ViewHolder(view) {
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
        view: View,
        onSpeciesClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onSpeciesClicked(adapterPosition)
            }
        }

        fun bind(item: Species) {
            // bind data to your view
            Log.d("Adapter", item.name)
            val textView = itemView.findViewById<TextView>(R.id.species_name_textview)
            val imageView = itemView.findViewById<ImageView>(R.id.species_thumbnail_imageview)
            imageView.loadUrl(getPokemonSpeciesImageUrl(item.id))

            textView.text = item.name
        }
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Species>() {
    override fun areItemsTheSame(oldItem: Species, newItem: Species): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Species, newItem: Species): Boolean =
        oldItem == newItem
}