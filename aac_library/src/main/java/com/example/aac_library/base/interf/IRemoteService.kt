package com.example.aac_library.base.interf

import com.example.aac_library.base.ICallback
import com.example.aac_library.eventbus.OkBinder

/**
 * @author: JingYuchun
 * @date: 2019/7/23 20:29
 * @desc: 服务端提供的能力
 */
@OkBinder.Interface
interface IRemoteService {
    /**
     * 通过调用服务端的接口 取得响应结果数据
     */
    fun doSomething(data:String,callback:ICallback)
}