package com.example.aacdemo.demo.observer_pattern

/**
 * @author: JingYuchun
 * @date: 2019/8/1 14:56
 * @desc: 回调界面消息列表刷新
 */
interface ObserverListener {
    fun notifyChangeData(messageDatas:MutableList<Message>)
}