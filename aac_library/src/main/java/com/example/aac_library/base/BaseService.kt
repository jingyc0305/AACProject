package com.example.aac_library.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.aac_library.base.interf.IRemoteService
import com.example.aac_library.eventbus.OkBinder

/**
 * @author: JingYuchun
 * @date: 2019/7/23 20:28
 * @desc: 基础服务类,具备进程间通讯能力
 */
@OkBinder.Interface
interface ICallback {
    val data: String
    fun onResult(result: String)
}

abstract class BaseService : Service() {
    private val okBinder = OkBinder(object : IRemoteService {

        override fun getDataCallBack(data: String, callback: ICallback) {
            Log.d("okbinder", ">> **data = $data ** <<")
            Log.d("okbinder", ">> **callback.data = ${callback.data} ** <<")
            callback.onResult("I am from binder callback data")
        }
    })

    override fun onBind(intent: Intent?): IBinder? {
        return okBinder
    }
}