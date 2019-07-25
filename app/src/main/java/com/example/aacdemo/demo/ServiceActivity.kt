package com.example.aacdemo.demo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.aac_library.base.ICallback
import com.example.aac_library.base.interf.IRemoteService
import com.example.aac_library.eventbus.OkBinder
import com.example.aacdemo.R
import com.example.aacdemo.service.MyService
import kotlinx.android.synthetic.main.activity_service.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author: JingYuchun
 * @date: 2019/7/23 20:38
 * @desc:
 */
class ServiceActivity : AppCompatActivity(),ServiceConnection{
    private var remoteService:IRemoteService? = null
    private var isServiceConnected = false
    override fun onServiceDisconnected(p0: ComponentName?) {
        isServiceConnected = false
    }


    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        isServiceConnected = true
        remoteService = OkBinder.proxy(p1!!,IRemoteService::class.java)
    }


    override fun onStart() {
        super.onStart()
        /**
         * 绑定服务
         */
        rebindService(MyService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        button.onClick {
            if (remoteService != null) {
                remoteService?.getDataCallBack("hello binder",object:ICallback{
                    override val data: String
                        get() = ""

                    override fun onResult(result: String) {
                        Log.d("okbinder", "result=$result")
                        binder_result_tv.text = result
                    }
                })
            }
        }
    }

    private fun rebindService(serviceClass: Class<*>) {
        if (isServiceConnected) {
            unbindService(this)
        }
        bindService(Intent(this, serviceClass), this, Context.BIND_AUTO_CREATE)
        isServiceConnected = true
    }
}