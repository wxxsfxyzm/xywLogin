package com.carlyu.logindemo.utils

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 扩展函数类
 */

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun <T> jsonToList(jsonList: String): List<T> {
    return Gson().fromJson(jsonList, object : TypeToken<ArrayList<T>>() {}.type)
}