package com.example.aacdemo.base.interf

import androidx.lifecycle.MutableLiveData
import com.example.aacdemo.base.event.BaseActionEvent

interface IViewModelAction {

    val actionLiveData: MutableLiveData<BaseActionEvent>

    fun startLoading()

    fun startLoading(msg: String)

    fun dismissLoading()

    fun showToast(msg: String)

    fun finish()

    //    ...
}
