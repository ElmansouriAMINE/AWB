package com.example.testoo.ViewModels

import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testoo.Common.Resource
import com.example.testoo.UI.States.AgencesWafacachListState
import com.example.testoo.UI.States.GabsListState
import com.example.testoo.UI.UseCase.GetAgencesWafaCashNearUseCase
import com.example.testoo.UI.UseCase.GetGabsNearUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@RequiresApi(34)
@HiltViewModel
class GabViewModel @Inject constructor(
    private val getGabsNearUseCase: GetGabsNearUseCase
): ViewModel() {

    private val _gabsState = MutableStateFlow(GabsListState())
    val gabsState: StateFlow<GabsListState> = _gabsState

    init {
        getGabsNear()
    }

    @RequiresApi(34)
    private fun getGabsNear() {
        getGabsNearUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _gabsState.value = GabsListState(
                        gabsList = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _gabsState.value = GabsListState(
                        error = result.message ?: "An Unexpected error has occurred"
                    )
                }
                is Resource.Loading -> {
                    _gabsState.value =GabsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
