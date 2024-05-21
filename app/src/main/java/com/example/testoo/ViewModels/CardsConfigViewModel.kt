package com.example.testoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testoo.Domain.models.ImageItem

class CardsConfigViewModel : ViewModel() {

    private val _currentCardItem = MutableLiveData<ImageItem>()
    val currentCardItem : LiveData<ImageItem> get() = _currentCardItem


    fun setCurrentCardItem(currentCardItem : ImageItem){
        _currentCardItem.value = currentCardItem
    }

}
