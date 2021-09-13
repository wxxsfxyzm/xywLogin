package com.carlyu.logindemo.bean

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
    val userid: String,

    val password: String,
    val username: String,
    val balance: BigDecimal
) : Serializable