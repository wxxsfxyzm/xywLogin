package com.carlyu.xywlogin.login

import com.carlyu.xywlogin.base.BasePresenter
import com.carlyu.xywlogin.base.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {

        /**
         * 获取用户id
         */
        fun getUserById(): String

        /**
         * 获取用户密码
         */
        fun getPwd(): String

        /**
         * 登陆成功
         */
        fun loginSuccess()

        /**
         * 登陆失败
         */
        fun loginFail(msg: String)

    }

    interface Presenter : BasePresenter {
        /**
         * 开始登录
         * 声明网络类型
         */
        fun goLogin(userid: String, password: String, netType: String)

        interface OnLoginCallBack {
            fun loginSuccess() // 传入空参，仅供测试
            fun loginFail(message: String)
        }

    }

    interface Task {
        fun login(
            userid: String?,
            password: String?,
            R1: String,
            R3: String,
            R6: String,
            para: String,
            Key: String,
            netType: String,
            onLoginCallBack: Presenter.OnLoginCallBack
        )
    }

}