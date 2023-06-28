package com.terrellewis.pokemonspecies.species.presentation.species_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.color.MaterialColors
import com.terrellewis.pokemonspecies.R
import com.terrellewis.pokemonspecies.core.utils.getPokemonSpeciesImageUrl
import com.terrellewis.pokemonspecies.core.utils.loadUrl
import com.terrellewis.pokemonspecies.core.utils.setSize
import com.terrellewis.pokemonspecies.databinding.FragmentSpeciesDetailBinding
import com.terrellewis.pokemonspecies.species.domain.model.SpeciesDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpeciesDetailFragment : DialogFragment() {

    private var _binding: FragmentSpeciesDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SpeciesDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpeciesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setSize(
            widthPercentage = 100,
            heightPercentage = 85
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val speciesId = arguments?.getInt(SPECIES_ID) ?: 1

        binding.closeImageview.setOnClickListener {
            dialog?.cancel()
        }

        lifecycleScope.launch {
            viewModel.getSpeciesDetailAndFirstEvolution(speciesId)
                .observe(viewLifecycleOwner) { result ->
                    if (result.isSuccess) {
                        result.getOrNull()?.let {
                            presentSpeciesDetails(it)
                        } ?: run {
                            // Handle the null case
                            Log.e("SpeciesDetailFragment", "Error occurred while fetching species detail")
                        }
                    } else {
                        Log.e("SpeciesDetailFragment", "${result.exceptionOrNull()?.message}")
                    }
                }
        }
    }

    private fun presentSpeciesDetails(speciesDetail: SpeciesDetail) {
        Log.d("SpeciesDetail", speciesDetail.toString())

        binding.speciesNameTextview.text = speciesDetail.name
        binding.speciesFlavorTextview.text =
            speciesDetail.flavorText.trim().replace("\n", " ")
        binding.captureRateTextview.text =
            speciesDetail.captureRateDifference.toString()
        if (speciesDetail.captureRateDifference > 0) {
            binding.captureRateTextview.setTextColor(
                MaterialColors.getColor(
                    binding.captureRateTextview,
                    R.attr.colorSuccess
                )
            )
        } else if (speciesDetail.captureRateDifference < 0) {
            binding.captureRateTextview.setTextColor(
                MaterialColors.getColor(
                    binding.captureRateTextview,
                    androidx.appcompat.R.attr.colorError
                )
            )
        }
        val speciesImageUrl = getPokemonSpeciesImageUrl(speciesDetail.id)
        binding.speciesImageImageview.loadUrl(speciesImageUrl)
        binding.evolutionNameTextview.text = speciesDetail.evolvesTo?.name ?: ""

        speciesDetail.evolvesTo?.let {
            binding.evolutionNameTextview.text = it.name
            val evolveToImageUrl = getPokemonSpeciesImageUrl(it.id)
            binding.evolutionThumbnailImageview.loadUrl(evolveToImageUrl)
        } ?: kotlin.run {
            binding.evolutionThumbnailImageview.visibility = GONE
            binding.evolutionNameTextview.text =
                getString(R.string.reached_final_form_in_evolution_chain)
        }
    }

    companion object {
        const val SPECIES_ID = "speciesId"
    }
}