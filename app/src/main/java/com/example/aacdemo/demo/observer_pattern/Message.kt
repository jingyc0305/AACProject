package com.example.aacdemo.demo.observer_pattern

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author: JingYuchun
 * @date: 消息体
 * @desc:
 */
data class Message(var message: String, private val itemType: Int) : MultiItemEntity {

    companion object {
        //房东
        const val OWENER = 0
        //租客1
        const val TENANT_ONE = 1
        //租客2
        const val TENANT_TWO = 2
    }

    override fun getItemType(): Int {
        return itemType
    }
}
