package com.example.testforxml

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.testforxml.databinding.FragmentFirstBinding
import com.example.testforxml.feature_address_search.domain.model.LocalAddress
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddressSearchFragment : Fragment() {

    private var transformedList: MutableList<LocalAddress> = mutableListOf()
    private val localAddressAdapter by lazy {
        AddressListAdapter()
    }
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    // TODO : Armazenar os dados no room antes de carregar na UI.
    // TODO : Implementar uma busca.
    // TODO : Aplicar Arquitetura Clean + MVVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAddressFilePresent()) {
            downloadFile()
        } else {
            createAdapterAndShowList()
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
    }

    private fun mapFileDataIntoObjectList(file: File): MutableList<LocalAddress> {
        val readList = file.readLines()
        transformedList = mutableListOf<LocalAddress>()
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

    private fun downloadFile() {
        val apiInterface = GitHubService.create().getCodigosPostais()
        binding.progressBar.isVisible = true
        apiInterface.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>?,
                response: Response<ResponseBody>?
            ) {
                response?.run {
                    if (response.isSuccessful) {
                        this.body()?.run { saveFileToDisk(this) }
                    }
                }
                binding.progressBar.isVisible = false
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Error : $t", Toast.LENGTH_SHORT)
                    .show()
                binding.progressBar.isVisible = false
            }
        })
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
        const val FILE_NAME = "codigosPostaisNovo"
    }
}
