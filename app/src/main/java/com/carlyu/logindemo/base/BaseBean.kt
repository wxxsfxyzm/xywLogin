package com.carlyu.logindemo.base

import com.carlyu.logindemo.common.MyApplication.Companion.context
import com.carlyu.logindemo.utils.toast
import java.io.Serializable

/**
 * Created by Roman on 2021/1/26
 */
class BaseBean<T> :Serializable {

    var data: T? = null

    //	SUCCESS
    private var resultCode : String? = null
    //	成功,系统处理正常
    private var resultDesc: String? = null

    fun isSuccess(): Boolean {
        return "SUCCESS" == resultCode
    }

    fun showErrorMsg() {
        context?.toast(resultDesc.toString())
    }
}