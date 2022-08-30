package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devarthur4718.searchaddressapp.databinding.FragmentSearchAddressBinding
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressSearchFragment : Fragment() {

    private val localAddressAdapter by lazy {
        AddressListAdapter()
    }
    private var _binding: FragmentSearchAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddressesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addressesState().observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe
            when (state) {
                is AddressState.Loading -> showProgress()
                is AddressState.OnRemoteAddressFileReceived -> {
                    hideProgress()
                    LocalAddress.saveFile(requireContext(), state.data)
                    createAdapterAndShowList()
                }
                is AddressState.OnDataSaved -> {
                    hideProgress()
                    viewModel.getDataFromLocal()
                }
                is AddressState.OnAddressesFetchedFromLocal -> {
                    hideProgress()
                    localAddressAdapter.submitList(state.list)
                    binding.rvAddressList.adapter = localAddressAdapter
                }
                is AddressState.OnQueryFinished -> {
                    hideProgress()
                    val newAdapter = AddressListAdapter()
                    binding.rvAddressList.adapter = newAdapter
                    newAdapter.submitList(state.querryList)
                }
                is AddressState.Error -> {
                    hideProgress()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchAddress(it) }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let { viewModel.searchAddress(it) }
                return true
            }
        })

        viewModel.getAddressFromRemoteAndSaveLocally()
    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }

    private fun createAdapterAndShowList() {
        val addressList = LocalAddress.mapFileDataIntoObjectList(requireContext())
        viewModel.handleAddressesFileIntoDatabase(addressList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_CODE_WRITE_EXTERNAL = 1
    }
}
