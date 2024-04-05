package com.example.testoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VirementViewModel : ViewModel() {
    private val _data =MutableLiveData<String>()
    val data: LiveData<String> get() = _data


    fun setData(data:String){
        _data.value= data
    }
}
