package com.example.testoo.ViewModels

import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testoo.Common.Resource
import com.example.testoo.Data.remote.Dto.AgenceWafaCashDto
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.example.testoo.UI.States.AgencesWafacachListState
import com.example.testoo.UI.UseCase.GetAgencesWafaCashNearUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@RequiresApi(34)
@HiltViewModel
class WafaCashViewModel @Inject constructor(
    private val getAgencesWafaCashNearUseCase: GetAgencesWafaCashNearUseCase
): ViewModel() {

    private val _agencesWafaCashState = MutableStateFlow(AgencesWafacachListState())
    val agencesWafaCashState: StateFlow<AgencesWafacachListState> = _agencesWafaCashState

    init {
        getAgencesWafacashNear()
    }

    @RequiresApi(34)
    private fun getAgencesWafacashNear() {
        getAgencesWafaCashNearUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _agencesWafaCashState.value = AgencesWafacachListState(
                        agencesWafaCashList = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _agencesWafaCashState.value = AgencesWafacachListState(
                        error = result.message ?: "An Unexpected error has occurred"
                    )
                }
                is Resource.Loading -> {
                    _agencesWafaCashState.value = AgencesWafacachListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

//@HiltViewModel
//class WafaCashViewModel @Inject constructor(
//    private val getAgencesWafaCashNearUseCase: GetAgencesWafaCashNearUseCase
//):ViewModel() {
//
//    private val getAgencesWafaCashNear = MutableStateFlow(AgencesWafacachListState())
//    var _getAgencesWafaCashNear: StateFlow<AgencesWafacachListState> = getAgencesWafaCashNear
//
//    @RequiresApi(34)
//    private fun getAgencesWafacashNear() {
//        getAgencesWafaCashNearUseCase().onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//
//                    println("hoho"+getAgencesWafaCashNear.value)
//
//                    getAgencesWafaCashNear.value =
//                        AgencesWafacachListState(agencesWafaCashList = (result.data) as List<List<AgenceWafaCashDto>>)
//
//                }
//                is Resource.Error -> {
//                    AgencesWafacachListState(
//                        error = result.message ?: "An Unexpected error has occured"
//                    )
//
//                }
//                is Resource.Loading -> {
//                    AgencesWafacachListState(isLoading = true)
//
//                }
//            }
//
//
//        }
//    }
//}


//@HiltViewModel
//class WafaCashViewModel @Inject constructor(
//    private val getAgencesWafaCashNearUseCase: GetAgencesWafaCashNearUseCase
//): ViewModel() {
//
//    // MutableStateFlow pour émettre les marqueurs d'agences
//    private val agencesMarkers = MutableStateFlow<List<MarkerOptions>>(emptyList())
//    val agencesMarkersState: StateFlow<List<MarkerOptions>> = agencesMarkers
//
//    @RequiresApi(34)
//    internal fun getAgencesWafacashNear() {
//        getAgencesWafaCashNearUseCase().onEach { result ->
//            when(result) {
//                is Resource.Success -> {
//                    val agences = result.data ?: emptyList()
//                    // Convertir les agences en marqueurs
//                    val markers = agences.map { agence ->
//                        MarkerOptions()
//                            .position(LatLng(agence.latitude.toDouble(), agence.longitude.toDouble()))
//                            .title(agence.nom)
//                    }
//                    // Émettre les marqueurs
//                    agencesMarkers.value = markers
//                }
//                is Resource.Error -> {
//                    AgencesWafacachListState(error = result.message?: "An Unexpected error has occured")
//
//                }
//                is Resource.Loading -> {
//                    AgencesWafacachListState(isLoading = true)
//
//                }
//
//            }
//        }
//    }
//}











