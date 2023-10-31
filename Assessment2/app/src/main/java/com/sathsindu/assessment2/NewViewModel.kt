package com.sathsindu.assessment2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewViewModel: ViewModel() {
    private val mutableData = MutableLiveData<List<String>>(emptyList())
    val data: LiveData<List<String>> get() = mutableData

    fun setData(data: List<String>) {
        mutableData.value = data

    }
}