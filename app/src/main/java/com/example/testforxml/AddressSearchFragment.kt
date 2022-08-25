package com.example.testforxml

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.testforxml.databinding.FragmentFirstBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddressSearchFragment : Fragment() {

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
    }

    private fun mapFileDataIntoObjectList(file: File): MutableList<Address> {
        val readList = file.readLines()
        val transformedList = mutableListOf<Address>()
        for (line in readList) {
            val dataSelected = line.split(",")
            transformedList.add(
                Address(
                    dataSelected.last(),
                    "${dataSelected.get(dataSelected.lastIndex - 2)}-${dataSelected.get(dataSelected.lastIndex - 1)}"
                )
            )
        }
        transformedList.removeAt(0)
        return transformedList
    }

    private fun deleteFile() {
        requireContext().deleteFile(FILE_NAME)
        Toast.makeText(requireContext(), "File deleted", Toast.LENGTH_SHORT).show()
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
        val addressAdapter = AddressListAdapter()
        addressAdapter.submitList(addressList)
        binding.rvAddressList.adapter = addressAdapter
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
