package com.carlyu.xywlogin.utils

import android.content.Context
import android.net.ConnectivityManager

object ConnectUtils {
    /**
     * 判断网络是否可用
     * */
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo?.isAvailable ?: false
    }

    /**
     * 判断网络是否连接
     * */
    fun isConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }

    /**
     * 获取网络类型
     * */
    fun getNetworkType(context: Context): String? {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo?.typeName
    }

    /**
     * 判断是否是WIFI网络
     * */
    fun isWifiNetwork(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return networkInfo != null && networkInfo.isConnected
    }

    /**
     * 判断是否是Mobile类型
     * */
    fun isMobileNetwork(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return networkInfo != null && networkInfo.isConnected
    }

    /**
     * 设置网络接口
     *
     * @param context this-当前应用上下文
     * @param networkPreference
     *  ConnectivityManager.TYPE_WIFI Wi-Fi接口
     *  ConnectivityManager.TYPE_MOBILE 数据接口
     */
    fun setNetworkPreferences(context: Context, networkPreference: String) {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        manager.networkPreference = ConnectivityManager.TYPE_WIFI
    }
}