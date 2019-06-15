package com.example.aac_library.base.interf

import androidx.lifecycle.MutableLiveData
import com.example.aac_library.base.event.BaseActionEvent

interface IViewModel {

    val actionLiveData: MutableLiveData<BaseActionEvent>

    fun startLoading()

    fun startLoading(msg: String)

    fun dismissLoading()

    fun showToast(msg: String)

    fun finish()

    //    ...
}
