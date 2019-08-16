package com.example.business_library

import com.example.business_library.bean.BLEState

/**
 * @author: JingYuchun
 * @date: 2019/8/5 11:39
 * @desc: 蓝牙连接监听
 */
interface BlEConnectCallBack {
    fun onBLEConnectSucess(bleState: BLEState)

    fun onBleConnectFailed(bleState: BLEState)

}