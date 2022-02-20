package com.carlyu.xywlogin.common

import android.os.Build
import com.carlyu.xywlogin.base.BaseConstant

/**
 * 常量
 */
object Constant {
    const val S:Int= Build.VERSION_CODES.S

    const val CMCC_URL: String = BaseConstant.BASE_URL_CMCC
    const val FYOUNG_URL: String = BaseConstant.BASE_URL_FYOUNG
    const val NJFU_WIFI_LIB_FIVE: String = BaseConstant.BASE_URL_NJFU_WIFI_LIB_FIVE
    const val NJFU_WIFI_NINE: String = BaseConstant.BASE_URL_NJFU_WIFI_NINE

    const val LATEST_VERSION: Int = BaseConstant.USERDATABASE_LATEST_VERSION

    enum class ResultEnum(val code: Int, val message: String) {
        ERROR(-1, "ERROR"),
        NETWORK_SELECTION_ERROR(-2, "网络选择错误"),

        NET_TYPE_ERROR(-100, "NetType参数错误"),
        IP_TYPE_ERROR(-101, "IpType参数错误"),

        INTERNAL_ERROR(-254, "Internal Error."),
        UNKNOWN_ERROR(-255, "Unknown Error.")
        ;// 最后一个要用;分开

    }

}