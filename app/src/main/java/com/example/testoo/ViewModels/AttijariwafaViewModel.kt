package com.example.testoo.ViewModels

import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testoo.Common.Resource
import com.example.testoo.UI.States.AgencesAttijariWafaListState
import com.example.testoo.UI.States.AgencesWafacachListState
import com.example.testoo.UI.States.GabsListState
import com.example.testoo.UI.UseCase.GetAgencesAttijariWafaNearUseCase
import com.example.testoo.UI.UseCase.GetGabsNearUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject



@RequiresApi(34)
@HiltViewModel
class AttijariwafaViewModel @Inject constructor(
    private val getAgencesAttijariWafaNearUseCase: GetAgencesAttijariWafaNearUseCase
): ViewModel() {

    private val _agenceAttijariWafaState = MutableStateFlow(AgencesAttijariWafaListState())
    val agenceAttijariWafaState: StateFlow<AgencesAttijariWafaListState> = _agenceAttijariWafaState

    init {
        getAttijariWafaNear()
    }

    @RequiresApi(34)
    private fun getAttijariWafaNear() {
        getAgencesAttijariWafaNearUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _agenceAttijariWafaState.value = AgencesAttijariWafaListState(
                        agencesAttijariWafaList = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _agenceAttijariWafaState.value = AgencesAttijariWafaListState(
                        error = result.message ?: "An Unexpected error has occurred"
                    )
                }
                is Resource.Loading -> {
                    _agenceAttijariWafaState.value =AgencesAttijariWafaListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
