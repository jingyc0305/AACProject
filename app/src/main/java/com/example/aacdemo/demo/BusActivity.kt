package com.example.aacdemo.demo

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.aac_library.eventbus.LiveDataBus
import com.example.aacdemo.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_live_bus.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author: JingYuchun
 * @date: 2019/8/1 10:14
 * @desc:
 */
class BusActivity : AppCompatActivity() {
    var timer: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_bus)
        button_live_data_bus.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        var count = 3
        LiveDataBus.get().with("change_name")?.value = "仲夏"
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 10)
        ToastUtils.setMsgColor(resources.getColor(R.color.white))
        ToastUtils.setMsgTextSize(14)
        ToastUtils.setBgColor(resources.getColor(R.color.colorPrimary))
        timer = Observable
            .interval(0, 1, TimeUnit.SECONDS)
            .map { along -> count - along }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { num ->
                time_tv.text = ""+num
                if(num == 1L){
                    ToastUtils.showShort("修改成功,即将回到上一页!")
                }
                if (num == 0L){
                    finish() //倒计时3s退出界面
                }
            })


    }


    override fun onDestroy() {
        super.onDestroy()
        if (null != timer && !timer!!.isDisposed) {
            timer?.dispose()
            timer = null
        }
    }
}

