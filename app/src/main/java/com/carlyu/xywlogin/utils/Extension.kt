package com.carlyu.xywlogin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.Log
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

fun isDarkMode(context: Context): Boolean {
    val currentNightMode = (context.resources.configuration.uiMode
            and Configuration.UI_MODE_NIGHT_MASK)
    val isDark = currentNightMode == Configuration.UI_MODE_NIGHT_YES
    Log.d("isDark", isDark.toString())
    return isDark
}

@SuppressLint("InternalInsetResource")
fun getStatusBarHeight(context: Context): Int {
    var height = 0
    val resourceId: Int = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        height = context.resources.getDimensionPixelSize(resourceId)
    }
    return height
}