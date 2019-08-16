package com.example.aacdemo.demo.observer_pattern

import android.util.Log
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch

/**
 * @author: JingYuchun
 * @date: 2019/6/27 9:50
 * @desc: 房东-具体被观察者
 */
class HouseOwener : RoomSubject{
    private var messageDatas:MutableList<Message>? = mutableListOf()
    var maps  = ConcurrentHashMap<String,RoomObserver>()
    override fun registerObserver(tenant: Tenant) {
        maps[tenant.getName()] = tenant
    }

    override fun removeObserver(tenant: Tenant) {
        maps.remove(tenant.getName())
    }

    override fun notifyObserver(message:String,thingType:ThingType)
    {

        messageDatas?.add(Message(message,Message.OWENER))
        Log.i("JYC",message)
        val beginTime = System.currentTimeMillis()

        val latch = CountDownLatch(maps.size)

        var i = 0
        maps.forEach {
            //it.value.payRent(moneyOfMonth)
            val message = MessageThread(latch, it.value as Tenant,thingType,message,messageDatas!!)
            message.start()
            i++
        }
        latch.await()
        val finishTime = System.currentTimeMillis()
        Log.i("JYC","房东消息发生完毕,耗时${finishTime-beginTime}")
    }
}

class MessageThread(private val latch: CountDownLatch,private val tenant: Tenant,private val thingType:ThingType,private val message:String,private val messageDatas:MutableList<Message>) : Thread() {

    override fun run() {

        try {
            sleep(1500)
            var flag = tenant.getName().contains("1")
            var type = if (flag){
                Message.TENANT_ONE
            }else{
                Message.TENANT_TWO
            }
            when(thingType){

                ThingType.MESSAGE-> {
                    messageDatas.add(Message(tenant.getName()+"收到通知",type))
                    tenant.reciveMsg(ThingType.MESSAGE,message,messageDatas)
                }
                ThingType.PAY-> {
                    messageDatas.add(Message(tenant.getName()+"交了"+tenant.getMoney(),type))
                    tenant.reciveMsg(ThingType.PAY,message,messageDatas)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            latch.countDown()
        }
    }


}
