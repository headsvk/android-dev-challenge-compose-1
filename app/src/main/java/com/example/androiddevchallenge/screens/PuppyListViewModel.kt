package com.example.androiddevchallenge.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.repositories.PuppyRepository
import com.example.androiddevchallenge.repositories.PuppyVo
import kotlinx.coroutines.launch

class PuppyListViewModel : ViewModel() {
    private val _itemList = MutableLiveData<List<PuppyVo>>()
    val itemList: LiveData<List<PuppyVo>>
        get() = _itemList

    init {
        viewModelScope.launch {
            _itemList.value = PuppyRepository.listPuppies()
        }
    }
}
