package com.example.aacdemo.demo.design_pattern.observer_pattern

import android.util.Log
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch

/**
 * @author: JingYuchun
 * @date: 2019/6/27 9:50
 * @desc: 房东-具体被观察者
 */
class HouseOwener : RoomSubject{
    var maps  = ConcurrentHashMap<String,RoomObserver>()
    override fun registerObserver(tenant: Tenant) {
        maps[tenant.getName()] = tenant
    }

    override fun removeObserver(tenant: Tenant) {
        maps?.remove(tenant.getName())
    }

    override fun notifyObserver(message:String,thingType:ThingType)
    {
        Log.i("JYC",message)

        val beginTime = System.currentTimeMillis()

        val latch = CountDownLatch(maps.size)

        var i = 0
        maps.forEach {
            //it.value.payRent(moneyOfMonth)
            val message = MessageThread(latch, it.value as Tenant,thingType,message)
            message.start()
            i++
        }
        latch.await()
        val finishTime = System.currentTimeMillis()
        Log.i("JYC","房东消息发生完毕,耗时${finishTime-beginTime}")
    }
}

class MessageThread(private val latch: CountDownLatch,private val tenant: Tenant,private val thingType:ThingType,private val message:String) : Thread() {

    override fun run() {

        try {
            sleep(1000)
            when(thingType){
                ThingType.MESSAGE-> tenant.reciveMsg(ThingType.MESSAGE,message)
                ThingType.PAY-> tenant.reciveMsg(ThingType.PAY,message)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            latch.countDown()
        }
    }


}
