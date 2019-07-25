package com.example.aacdemo.demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel(){
    var name:MutableLiveData<String>? = MutableLiveData()
}