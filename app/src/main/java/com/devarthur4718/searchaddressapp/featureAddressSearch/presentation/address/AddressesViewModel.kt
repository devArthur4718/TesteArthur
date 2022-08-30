package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.core.StandardErrorMessages
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase.DownloadCSVFileFromRemoteServer
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase.GetDataFromLocalStorage
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase.SaveDataIntoLocalStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val downloadCSVFileFromRemoteServer: DownloadCSVFileFromRemoteServer,
    private val saveDataIntoLocalStore: SaveDataIntoLocalStore,
    private val getDataFromLocalStorage: GetDataFromLocalStorage
) : ViewModel() {

    private val _addressesState = MutableLiveData<AddressState>()
    fun addressesState(): LiveData<AddressState> = _addressesState

    fun getAddressFromRemoteAndSaveLocally() {
        downloadCSVFileFromRemoteServer().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _addressesState.postValue(AddressState.Loading)
                }
                is Resource.Error -> {
                    _addressesState.postValue(
                        AddressState.Error(
                            result.message ?: StandardErrorMessages.HTTP_EXCEPTION_ERROR
                        )
                    )
                }
                is Resource.Success -> {
                    _addressesState.postValue(
                        result.data?.let {
                            AddressState.OnRemoteAddressFileReceived(
                                it
                            )
                        }
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun handleAddressesFileIntoDatabase(addressList: MutableList<LocalAddress>) {
        saveDataIntoLocalStore(addressList).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _addressesState.postValue(AddressState.Loading)
                }
                is Resource.Success -> {
                    _addressesState.postValue(AddressState.OnDataSaved)
                }
                is Resource.Error -> {
                    _addressesState.postValue(
                        AddressState.Error(
                            result.message ?: StandardErrorMessages.HTTP_EXCEPTION_ERROR
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDataFromLocal() {
        getDataFromLocalStorage().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _addressesState.postValue(AddressState.Loading)
                }
                is Resource.Error -> {
                    _addressesState.postValue(
                        AddressState.Error(
                            result.message ?: StandardErrorMessages.HTTP_EXCEPTION_ERROR
                        )
                    )
                }
                is Resource.Success -> {
                    _addressesState.postValue(
                        result.data?.let {
                            AddressState.OnAddressesFetchedFromLocal(
                                it
                            )
                        }
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
