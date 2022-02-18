package com.carlyu.xywlogin.utils

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log

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


    fun test2(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()

        // 设置指定的网络传输类型(蜂窝传输) 等于手机网络
        builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)

        // 设置感兴趣的网络功能
        // builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);

        // 设置感兴趣的网络：计费网络
        // builder.addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
        val request = builder.build()
        val callback: NetworkCallback = object : NetworkCallback() {
            /**
             * Called when the framework connects and has declared a new network ready for use.
             * This callback may be called more than once if the [Network] that is
             * satisfying the request changes.
             *
             */
            @TargetApi(Build.VERSION_CODES.M)
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.i("test", "已根据功能和传输类型找到合适的网络")

                // 可以通过下面代码将app接下来的请求都绑定到这个网络下请求
                connectivityManager.bindProcessToNetwork(network)

                // 也可以在将来某个时间取消这个绑定网络的设置
                // connectivityManager.bindProcessToNetwork(null);

                // 只要一找到符合条件的网络就注销本callback
                // 你也可以自己进行定义注销的条件
                connectivityManager.unregisterNetworkCallback(this)
            }
        }
        connectivityManager.requestNetwork(request, callback)
    }


}