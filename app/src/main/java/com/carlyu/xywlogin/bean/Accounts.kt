package com.carlyu.xywlogin.bean

import java.io.Serializable


/**
 * 登录返回
 */

data class Accounts(
    val code: String,
    val msg: String,
    val data: User
) : Serializable

data class User(
    val DDDDD: String,
    val upass: String,
    val R1: String = "0",
    val R3: String = "0",
    val R6: String = "00",
    val Key: String = "123456"
) : Serializable