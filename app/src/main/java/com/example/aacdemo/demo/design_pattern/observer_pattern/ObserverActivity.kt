package com.example.aacdemo.demo.design_pattern.observer_pattern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author: JingYuchun
 * @date: 2019/6/27 11:13
 * @desc: 学习观察者模式
 */
class ObserverActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //创建被观察者-房东
        val houseOwener = HouseOwener()

        //创建观察者-租客
        val tenant1 = Tenant("租客1",1200)
        val tenant2 = Tenant("租客2",1700)

        //订阅观察者-签订租房合同
        houseOwener.registerObserver(tenant1)
        houseOwener.registerObserver(tenant2)

        //发布消息-房东通知交房租
        houseOwener.notifyObserver("各位租客 房租涨价了200!",ThingType.MESSAGE)
        houseOwener.notifyObserver("各位租客 该交房租了,该交房租了!",ThingType.PAY)

    }
}