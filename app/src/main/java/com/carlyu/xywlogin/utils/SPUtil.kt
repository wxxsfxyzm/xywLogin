package com.carlyu.xywlogin.utils

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.carlyu.xywlogin.common.Constant
import com.carlyu.xywlogin.common.Constant.IS_LOGIN
import com.carlyu.xywlogin.common.MyApplication


object SPUtil {
    /**
     *
     */
    @Synchronized
    private fun getSP(): SharedPreferences {
        return MyApplication.context!!.getSharedPreferences("music", MODE_PRIVATE)
    }

    /**
     * 保存用户登录态
     *
     * 登陆成功则currentProgress为true
     * 否则为false
     */
    @Synchronized
    fun saveLogin(currentProgress: Boolean) {
        getSP().edit().putBoolean(IS_LOGIN, currentProgress).apply()
    }

    /**
     * 用户是否登录
     */
    @Synchronized
    fun isLogin(): Boolean {
        return getSP().getBoolean(Constant.IS_LOGIN, false)
    }
}