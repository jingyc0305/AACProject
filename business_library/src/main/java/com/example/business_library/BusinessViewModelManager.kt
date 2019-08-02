package com.example.business_library

/**
 * @author: JingYuchun
 * @date: 2019/8/1 20:54
 * @desc: 业务模块 所有viewmodel 管理
 *
 * 想法----用观察者模式 向上层提供接口访问
 */
class BusinessViewModelManager private constructor() {
    companion object {
        val instance: BusinessViewModelManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BusinessViewModelManager()
        }
    }
}