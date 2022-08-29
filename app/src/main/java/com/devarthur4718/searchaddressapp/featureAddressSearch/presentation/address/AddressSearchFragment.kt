package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.address

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devarthur4718.searchaddressapp.capitalizeAllWords
import com.devarthur4718.searchaddressapp.databinding.FragmentSearchAddressBinding
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.model.LocalAddress
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import java.io.File

@AndroidEntryPoint
class AddressSearchFragment : Fragment() {

    private var transformedList: MutableList<LocalAddress> = mutableListOf()
    private val localAddressAdapter by lazy {
        LocalAddressListAdapter()
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
                is AddressState.onRemoteAddressFileReceived -> {
                    hideProgress()
                    saveFileToDisk(state.data)
                }
                is AddressState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                localAddressAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                localAddressAdapter.filter.filter(query)
                return true
            }
        })

        if (!isAddressFilePresent()) {
            viewModel.getAddressFromRemoteAndSaveLocally()
        }
    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }

    private fun mapFileDataIntoObjectList(file: File): MutableList<LocalAddress> {
        val readList = file.readLines()
        transformedList = mutableListOf()
        for (line in readList) {
            val dataSelected = line.split(",")
            transformedList.add(
                LocalAddress(
                    dataSelected.last().toString().capitalizeAllWords(),
                    "${dataSelected[dataSelected.lastIndex - 2]}-${dataSelected[dataSelected.lastIndex - 1]}"
                )
            )
        }
        transformedList.removeAt(0)
        return transformedList
    }

    private fun isAddressFilePresent(): Boolean {
        return requireContext().fileList().contains(FILE_NAME)
    }

    private fun saveFileToDisk(body: ResponseBody) {
        requireContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(body.bytes())
        }
        createAdapterAndShowList()
    }

    private fun createAdapterAndShowList() {
        val file = File(requireContext().filesDir, FILE_NAME)
        val addressList = mapFileDataIntoObjectList(file)
        localAddressAdapter.addData(addressList)
        binding.rvAddressList.adapter = localAddressAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_CODE_WRITE_EXTERNAL = 1
        const val FILE_NAME = "postalCodeFile"
    }
}
