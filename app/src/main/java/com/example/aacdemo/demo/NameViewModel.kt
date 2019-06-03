package com.example.aacdemo.demo

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NameViewModel: ViewModel() {

    val currentName:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    fun log(view: View){
        Log.d("TAG","事件响应了")
    }

}