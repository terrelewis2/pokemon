package com.terrellewis.pokemonspecies.species.presentation.species_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.terrellewis.pokemonspecies.R
import com.terrellewis.pokemonspecies.databinding.FragmentSpeciesListBinding
import com.terrellewis.pokemonspecies.species.presentation.species_detail.SpeciesDetailFragment.Companion.SPECIES_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpeciesListFragment : Fragment() {
    private val viewModel by viewModels<SpeciesListViewModel>()

    private var _binding: FragmentSpeciesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpeciesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        val speciesRecyclerView = binding.speciesRecyclerview
        val speciesAdapter = SpeciesAdapter {
            val bundle = Bundle()
            bundle.putInt(SPECIES_ID, it)
            findNavController().navigate(
                R.id.action_speciesListFragment_to_speciesDetailFragment,
                bundle
            )
        }

        speciesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        speciesRecyclerView.adapter = speciesAdapter

        // Observe the data from ViewModel
        lifecycleScope.launch {
            viewModel.getSpeciesList().subscribe {
                Log.d("MainActivity", "Data collected:")
                speciesAdapter.submitData(lifecycle, it)
            }
        }
    }
}