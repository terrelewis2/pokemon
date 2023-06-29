package com.terrellewis.pokemonspecies.core.utils

import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.terrellewis.pokemonspecies.R

fun ImageView.loadUrl(url: String) {

    val imageLoader = ImageLoader.Builder(this.context)
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_error)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

//TODO: This should be remotely configurable
fun getPokemonSpeciesImageUrl(speciesId: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${speciesId}.png"
}