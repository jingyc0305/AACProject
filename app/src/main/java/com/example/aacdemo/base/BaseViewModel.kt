package com.example.aacdemo.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aacdemo.base.event.BaseActionEvent
import com.example.aacdemo.base.interf.IViewModelAction

open class BaseViewModel : ViewModel(), IViewModelAction {

    override val actionLiveData: MutableLiveData<BaseActionEvent> = MutableLiveData()
    open var lifecycleOwner: LifecycleOwner? = null


    override fun startLoading() {

        startLoading(null!!)
    }

    override fun startLoading(msg: String) {

        val baseActionEvent =
            BaseActionEvent(BaseActionEvent.SHOW_LOADING_DIALOG)
        baseActionEvent.message = msg
        actionLiveData.value = baseActionEvent
    }

    override fun dismissLoading() {
        actionLiveData.value =
            BaseActionEvent(BaseActionEvent.DISMISS_LOADING_DIALOG)

    }

    override fun showToast(msg: String) {

        val baseActionEvent =
            BaseActionEvent(BaseActionEvent.SHOW_TOAST)
        baseActionEvent.message = msg
        actionLiveData.value = baseActionEvent

    }

    override fun finish() {
        actionLiveData.value =
            BaseActionEvent(BaseActionEvent.FINISH)
    }
}
