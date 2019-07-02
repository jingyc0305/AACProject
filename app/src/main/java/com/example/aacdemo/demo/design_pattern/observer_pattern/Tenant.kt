package com.example.aacdemo.demo.design_pattern.observer_pattern

import android.util.Log

/**
 * @author: JingYuchun
 * @date: 2019/6/27 9:53
 * @desc: 具体观察者-租客
 */
class Tenant(private val name:String,private val money: Int) : RoomObserver {
    override fun reciveMsg(type: ThingType,message:String?) {
        when(type){
            ThingType.MESSAGE-> Log.i("JYC","${name}收到通知$message")
            ThingType.PAY-> Log.i("JYC","${name}交了${money}元房租!")
        }
    }
    fun getName():String = name
}