package com.example.aac_library.base

import com.google.gson.annotations.SerializedName

open class WanBaseResponse<T>(
    @SerializedName("errorCode") var code: Int = 0,

    @SerializedName("errorMsg") var msg: String? = null,

    @SerializedName("data") var data: T? = null
)
