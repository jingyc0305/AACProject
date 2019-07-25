package com.example.aacdemo.demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NameViewModel: ViewModel() {
    val currentName:MutableLiveData<String> = MutableLiveData()

}