package com.example.aac_library.base.interf


interface IBaseView {

    fun startLoading()

    fun startLoading(msg: String)

    fun dismissLoading()

    fun showError(msg:String)

    fun showEmpty()

    fun showToast(msg: String)

    fun finish()
}

