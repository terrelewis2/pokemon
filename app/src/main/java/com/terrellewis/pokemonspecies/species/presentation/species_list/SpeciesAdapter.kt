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
import com.terrellewis.pokemonspecies.core.utils.loadUrl
import com.terrellewis.pokemonspecies.species.domain.model.Species


class SpeciesAdapter : PagingDataAdapter<Species, SpeciesAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("Adapter", "Creating view holder")
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_species, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("Adapter", "Binding item: $item")
        if (item != null) {
            holder.bind(item)
        }
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Species) {
            // bind data to your view
            Log.d("Adapter", item.name)
            val textView = view.findViewById<TextView>(R.id.species_name_textview)
            val imageView = view.findViewById<ImageView>(R.id.species_thumbnail_imageview)
            val url = "https://unpkg.com/pokeapi-sprites@2.0.2/sprites/pokemon/other/dream-world/${item.id}.svg"
            Log.d("Adapter", url)
            imageView.loadUrl(url)

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