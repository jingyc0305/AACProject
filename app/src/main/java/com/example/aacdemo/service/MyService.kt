package com.example.aacdemo.service


import android.util.Log
import com.example.aac_library.base.BaseService
import com.example.aac_library.base.ICallback
import com.example.aac_library.base.interf.IRemoteService
import com.example.aac_library.eventbus.OkBinder

/**
 * @author: JingYuchun
 * @date: 2019/7/23 20:35
 * @desc: 测试进程间通讯demo
 */
class MyService : BaseService() {
    override fun initOkBinder(): OkBinder {
        return OkBinder(object : IRemoteService {

            override fun doSomething(data: String, callback: ICallback) {
                Log.d("okbinder", ">> **data = $data ** <<")
                Log.d("okbinder", ">> **callback.data = ${callback.data} ** <<")
                callback.onResult("I am from binder callback data")
            }
        })
    }
}