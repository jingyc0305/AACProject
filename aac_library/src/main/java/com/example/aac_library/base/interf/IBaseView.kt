package com.example.aac_library.base.interf

import androidx.lifecycle.MutableLiveData
import com.example.aac_library.base.event.BaseEvent

interface IViewModel {

    val actionLiveData: MutableLiveData<BaseEvent>

    fun startLoading()

    fun startLoading(msg: String)

    fun dismissLoading()

    fun showToast(msg: String)

    fun finish()

    //    ...
}
