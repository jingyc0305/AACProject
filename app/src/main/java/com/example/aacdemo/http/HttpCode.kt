package com.example.aacdemo.http
/**
 * @Author:JingYuchun
 * @Date:2019/6/3 13:37
 * @Description: 网络请求状态码
 */
object HttpCode {
    /**
     * 响应成功
     */
    @JvmField
    val SUCCESS = 0
    /**
     * 无网络
     */
    @JvmField
    val NO_NETWORK = 1001

    /**
     * 未知错误
     */
    @JvmField
    val UNKNOWN_ERROR = 1002

    /**
     * 服务器内部错误
     */
    @JvmField
    val SERVER_ERROR = 1003

    /**
     * 网络连接超时
     */
    @JvmField
    val NETWORK_ERROR = 1004

    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    @JvmField
    val API_ERROR = 1005
}