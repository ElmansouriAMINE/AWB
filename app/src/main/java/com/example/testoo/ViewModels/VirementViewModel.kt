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


    private val _virement= MutableLiveData<Transaction>()
    val virement:LiveData<Transaction> get() = _virement


    fun setVirement(data: Transaction){
        _virement.value= data
    }

    fun getVirement() =_virement


    fun setData(data:String){
        _data.value= data
    }

    fun setData2(data2:String){
        _data2.value =data2

    }
}
