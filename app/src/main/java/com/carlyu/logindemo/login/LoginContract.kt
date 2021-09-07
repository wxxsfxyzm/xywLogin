package com.carlyu.logindemo.login

import com.carlyu.logindemo.base.BasePresenter
import com.carlyu.logindemo.base.BaseView
import com.carlyu.logindemo.bean.Accounts

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
        fun loginSuccess(userAccount: Accounts)

        /**
         * 登陆失败
         */
        fun loginFail(msg: String)
    }

    interface Presenter : BasePresenter {
        /**
         * 开始登录
         */
        fun goLogin(userid: String, password: String)

        interface OnLoginCallBack {
            //fun loginSuccess() // 传入空参，仅供测试
            fun loginSuccess(userAccount: Accounts) // 传入用户信息
            fun loginFail(message: String)
        }
    }

    interface Task {
        fun login(
            userid: String?,
            password: String?,
            onLoginCallBack: Presenter.OnLoginCallBack
        )
    }

}