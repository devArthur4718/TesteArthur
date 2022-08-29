package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase.GetRemoteAddressesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val remoteAddressesUseCase: GetRemoteAddressesUseCase
) : ViewModel() {

    private val _addressesState = MutableLiveData<AddressState>()
    fun addressesState(): LiveData<AddressState> = _addressesState

    init {
        getAddressFromRemoteAndSaveLocally()
    }

    private fun getAddressFromRemoteAndSaveLocally() {
        remoteAddressesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _addressesState.postValue(AddressState.Loading)
                }
                is Resource.Error -> {
                    _addressesState.postValue(
                        AddressState.Error(
                            result.message ?: "An unexpected error ocurred"
                        )
                    )
                }
                is Resource.Success -> {
                    _addressesState.postValue(
                        result.data?.let {
                            AddressState.onRemoteAddressFileReceived(
                                it
                            )
                        }
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
