package com.example.aacdemo.demo.observer_pattern

/**
 * @author: JingYuchun
 * @date: 2019/6/27 9:51
 * @desc: 抽象观察者 -  接口
 */
interface RoomObserver {
    //交房租
    fun reciveMsg(type: ThingType,message:String?,messageDatas:MutableList<Message>)

}
enum class ThingType{
    PAY,
    MESSAGE
}