package com.example.aac_library.http
/**
 * @Author: JingYuchun
 * @Date: 2019/6/3 13:37
 * @Description: 网络请求状态码
 */
object HttpCode {
    /**
     * 响应成功
     */
    const val  SUCCESS = 0
    /**
     * 无网络
     */
    const val  NO_NETWORK = 1001

    /**
     * 未知错误
     */
    const val UNKNOWN_ERROR = 1002

    /**
     * 服务器内部错误
     */
    const val SERVER_ERROR = 1003

    /**
     * 网络连接超时
     */
    const val NETWORK_TIME_OUT = 1004

    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    const val  API_ERROR = 1005
    /**
     * 参数有误
     */
    const val PARAMETER_INVALID = 1006
    /**
     *  token 失效
     */
    const val TOKEN_INVALID = 1007
}