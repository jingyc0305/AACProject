package com.example.aacdemo.demo.observer_pattern

/**
 * @author: JingYuchun
 * @date: 2019/6/27 9:45
 * @desc: 抽象被观察者-接口
 */
interface RoomSubject {

    /**
     * 注册观察者
     */
    fun registerObserver(tenant: Tenant)
    /**
     * 移除观察者
     */
    fun removeObserver(tenant: Tenant)
    /**
     * 交房租
     */
    fun notifyObserver(message:String,thingType:ThingType)
}
