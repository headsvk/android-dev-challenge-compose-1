package com.example.androiddevchallenge.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.repositories.PuppyRepository
import com.example.androiddevchallenge.repositories.PuppyVo
import kotlinx.coroutines.launch

class PuppyDetailsViewModel : ViewModel() {
    private val _puppy = MutableLiveData<PuppyVo>()
    val puppy: LiveData<PuppyVo>
        get() = _puppy

    fun loadPuppy(id: String) {
        viewModelScope.launch {
            _puppy.value = PuppyRepository.getPuppy(id)
        }
    }
}
