package com.terrellewis.pokemonspecies.species.presentation.species_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.terrellewis.pokemonspecies.R
import com.terrellewis.pokemonspecies.core.utils.ApiErrorUtils.getErrorMessage
import com.terrellewis.pokemonspecies.databinding.FragmentSpeciesListBinding
import com.terrellewis.pokemonspecies.species.presentation.species_detail.SpeciesDetailFragment.Companion.SPECIES_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SpeciesListFragment : Fragment() {
    private val viewModel by viewModels<SpeciesListViewModel>()

    private var _binding: FragmentSpeciesListBinding? = null
    private var speciesAdapter: SpeciesAdapter? = null
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
        showList()
    }

    private fun showList() {
        // Initialize RecyclerView and Adapter
        val speciesRecyclerView = binding.speciesRecyclerview
        speciesAdapter = SpeciesAdapter {
            val bundle = Bundle()
            bundle.putInt(SPECIES_ID, it)
            findNavController().navigate(
                R.id.action_speciesListFragment_to_speciesDetailFragment,
                bundle
            )
        }
        speciesAdapter?.addLoadStateListener { loadingState ->
            setupLoadStateListener(loadingState)
        }
        val loaderStateAdapter = LoaderStateAdapter { speciesAdapter?.retry() }

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == speciesAdapter?.itemCount && loaderStateAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        speciesRecyclerView.layoutManager = gridLayoutManager
        speciesRecyclerView.adapter = speciesAdapter?.withLoadStateFooter(loaderStateAdapter)

        // Observe the data from ViewModel
        lifecycleScope.launch {
            viewModel.getSpeciesList().subscribe {
                speciesAdapter?.submitData(lifecycle, it)
            }
        }
        binding.errorLayout.retryButton.setOnClickListener {
            speciesAdapter?.retry()
        }
    }

    private fun setupLoadStateListener(loadingState: CombinedLoadStates) {
        _binding?.apply {
            loadingIndicator.visibility =
                if (loadingState.refresh is LoadState.Loading) View.VISIBLE else View.GONE

            speciesRecyclerview.visibility =
                if ((loadingState.refresh is LoadState.Error && speciesAdapter?.itemCount == 0)
                    || loadingState.refresh is LoadState.Loading
                ) View.GONE else View.VISIBLE

            errorLayout.root.visibility =
                if (loadingState.refresh is LoadState.Error && speciesAdapter?.itemCount == 0)
                    View.VISIBLE else View.GONE

            if (loadingState.refresh is LoadState.Error) {
                errorLayout.errorMessageTextview.text =
                    getErrorMessage((loadingState.refresh as LoadState.Error).error)
            }
        }
    }
}