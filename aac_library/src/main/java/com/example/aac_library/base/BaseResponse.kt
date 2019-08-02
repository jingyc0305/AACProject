package com.example.aac_library.base

open class BaseResponse<T>(
    var errorCode: Int = 0,

    var errorMsg: String? = "",

    var data: T? = null
)
