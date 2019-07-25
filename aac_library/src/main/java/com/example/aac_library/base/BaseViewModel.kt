package com.example.aac_library.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aac_library.base.event.BaseEvent
import com.example.aac_library.base.interf.IBaseView

open class BaseViewModel : ViewModel(), IBaseView {

    open val actionLiveData =  MutableLiveData<BaseEvent>()
    open var lifecycleOwner: LifecycleOwner? = null


    override fun startLoading() {

        startLoading("正在加载...")
    }

    override fun startLoading(msg: String) {

        val baseActionEvent =
            BaseEvent()
        baseActionEvent.action = BaseEvent.SHOW_LOADING_DIALOG
        baseActionEvent.message = msg
        actionLiveData.value = baseActionEvent
    }

    override fun dismissLoading() {
        val baseEvent = BaseEvent()
        baseEvent.action = BaseEvent.DISMISS_LOADING_DIALOG
        actionLiveData.value = baseEvent
    }

    override fun showToast(msg: String) {

        val baseActionEvent = BaseEvent()
        baseActionEvent.message = msg
        baseActionEvent.action = BaseEvent.SHOW_TOAST
        actionLiveData.value = baseActionEvent

    }

    override fun finish() {
        val baseActionEvent = BaseEvent()
        baseActionEvent.action = BaseEvent.FINISH
        actionLiveData.value = baseActionEvent
    }
}
