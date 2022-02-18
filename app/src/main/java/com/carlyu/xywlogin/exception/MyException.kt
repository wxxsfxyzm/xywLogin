package com.carlyu.xywlogin.exception

import com.carlyu.xywlogin.common.Constant.ResultEnum


class MyException : Exception {
    private val code: Int

    constructor(code: Int, message: String?) : super(message) {
        this.code = code
    }

    constructor(resultEnum: ResultEnum) : super(resultEnum.message) {
        this.code = resultEnum.code
    }
}
