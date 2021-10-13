package com.carlyu.xywlogin.bean

import java.io.Serializable
import java.math.BigDecimal


/**
 * 登录返回
 */

data class Accounts(
    val code: String,
    val msg: String,
    val data: User
) : Serializable

data class User(
    val studentId: String,
    val password: String,
    val studentName: String,
    val balance: BigDecimal
) : Serializable