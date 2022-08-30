package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.core.StandardErrorMessages
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase.GetAddressesFileUseCase
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase.SaveDataIntoRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val getAddressesFileUseCase: GetAddressesFileUseCase,
    private val saveDataIntoRoomUseCase: SaveDataIntoRoomUseCase
) : ViewModel() {

    private val _addressesState = MutableLiveData<AddressState>()
    fun addressesState(): LiveData<AddressState> = _addressesState

    fun getAddressFromRemoteAndSaveLocally() {
        getAddressesFileUseCase().onEach { result ->
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
        saveDataIntoRoomUseCase.saveDataIntoDatabase(addressList).onEach { result ->
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
        getAddressesFileUseCase.getDataFromDatabase().onEach { result ->
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
