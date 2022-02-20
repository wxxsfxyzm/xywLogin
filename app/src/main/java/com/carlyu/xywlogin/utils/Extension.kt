package com.carlyu.xywlogin.utils

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * 扩展函数类
 */

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}


/**
 * DP 转换 PX 的工具类
 */
fun dp2px(context: Context, dpVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpVal, context.resources.displayMetrics
    ).toInt()
}

fun <T> jsonToList(jsonList: String): List<T> {
    return Gson().fromJson(jsonList, object : TypeToken<ArrayList<T>>() {}.type)
}