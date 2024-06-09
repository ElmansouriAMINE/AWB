package com.example.testoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EntreeRelationViewModel : ViewModel(){
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    fun setEmail(data:String){
        _email.value= data
    }
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    fun setPassword(data:String){
        _password.value= data
    }
    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> get() = _phoneNumber

    fun setPhoneNumber(data:String){
        _phoneNumber.value= data
    }
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    fun setUserName(data:String){
        _userName.value= data
    }
    private val _confirmPass = MutableLiveData<String>()
    val confirmPass: LiveData<String> get() = _confirmPass

    fun setConfirmPass(data:String){
        _confirmPass.value= data
    }
    private val _employerName = MutableLiveData<String>()
    val employerName: LiveData<String> get() = _employerName

    fun setEmployerName(data:String){
        _employerName.value= data
    }
    private val _hiringDate = MutableLiveData<String>()
    val hiringDate: LiveData<String> get() = _hiringDate

    fun setHiringDate(data:String){
        _hiringDate.value= data
    }
    private val _previousEmployerName = MutableLiveData<String>()
    val previousEmployerName: LiveData<String> get() = _previousEmployerName

    fun setPreviousEmployerName(data:String){
        _previousEmployerName.value= data
    }
    private val _annualSalary = MutableLiveData<String>()
    val annualSalary: LiveData<String> get() = _annualSalary

    fun setAnnualSalary(data:String){
        _annualSalary.value= data
    }
    private val _anotherAccount = MutableLiveData<Boolean>()
    val anotherAccount: LiveData<Boolean> get() = _anotherAccount

    fun setAnotherAccount(data:Boolean){
        _anotherAccount.value= data
    }
    private val _usCitizen = MutableLiveData<Boolean>()
    val usCitizen: LiveData<Boolean> get() = _usCitizen

    fun setUsCitizen(data:Boolean){
        _usCitizen.value= data
    }
    private val _transferMoneyUS = MutableLiveData<Boolean>()
    val transferMoneyUS: LiveData<Boolean> get() = _transferMoneyUS

    fun setTransferMoneyUS(data:Boolean){
        _transferMoneyUS.value= data
    }
}
