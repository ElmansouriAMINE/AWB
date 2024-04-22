package com.example.testoo.ViewModels

import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.testoo.Common.Resource
import com.example.testoo.Domain.Repository.WafaCashRepository
import com.example.testoo.UI.States.AgencesWafacachListState
import com.example.testoo.UI.UseCase.GetAgencesWafaCashNearUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WafaCashViewModel @Inject constructor(
    private val getAgencesWafaCashNearUseCase: GetAgencesWafaCashNearUseCase
):ViewModel() {

        private val getAgencesWafaCashNear = MutableStateFlow(AgencesWafacachListState())
        var _getAgencesWafaCashNear : StateFlow<AgencesWafacachListState> = getAgencesWafaCashNear

    @RequiresApi(34)
    private fun getAgencesWafacashNear(){
        getAgencesWafaCashNearUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {

                    getAgencesWafaCashNear.value = AgencesWafacachListState(agencesWafaCashList = result.data?: emptyList())

                }
                is Resource.Error -> {
                    getAgencesWafaCashNear.value = AgencesWafacachListState(error = result.message?: "An Unexpected error has occured")

                }
                is Resource.Loading -> {
                    getAgencesWafaCashNear.value = AgencesWafacachListState(isLoading = true)

                }
            }


        }
    }













}
