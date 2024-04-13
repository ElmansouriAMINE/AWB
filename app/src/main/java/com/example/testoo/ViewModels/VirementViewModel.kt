package com.example.testoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testoo.models.Transaction

class VirementViewModel : ViewModel() {
    private val _data =MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    private val _data2 =MutableLiveData<String>()
    val data2: LiveData<String> get() = _data2


    private val _beneficiaire =MutableLiveData<String>()
    val beneficiaire: LiveData<String> get() = _beneficiaire

    private val _montant =MutableLiveData<String>()
    val montant: LiveData<String> get() = _montant

    private val _motif =MutableLiveData<String>()
    val motif: LiveData<String> get() = _motif




    fun setData(data:String){
        _data.value= data
    }

    fun setMontant(data:String){
        _montant.value= data
    }
    fun setMotif(data:String){
        _motif.value= data
    }
    fun setBeneficiaire(data:String){
        _beneficiaire.value= data
    }


    fun setData2(data2:String){
        _data2.value =data2

    }
}
