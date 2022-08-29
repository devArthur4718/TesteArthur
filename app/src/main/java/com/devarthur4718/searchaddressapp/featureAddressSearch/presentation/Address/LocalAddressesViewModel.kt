package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.Address

import androidx.lifecycle.ViewModel
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase.LocalAddresesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocalAddressesViewModel @Inject constructor(
    private val useCases: LocalAddresesUseCases
) : ViewModel(){

}
