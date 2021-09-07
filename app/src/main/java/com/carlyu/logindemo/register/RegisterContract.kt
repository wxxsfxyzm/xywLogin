package com.carlyu.logindemo.register

import com.carlyu.logindemo.base.BasePresenter
import com.carlyu.logindemo.base.BaseView
import com.carlyu.logindemo.bean.Accounts

/**
 * Created by Roman on 2021/1/26
 */
interface RegisterContract {

    interface View : BaseView<Presenter> {
        fun getUserById(): String
        fun getUserName(): String
        fun getPwd(): String
        fun getConfirmPwd(): String
        fun registerSuccess(userAccount: Accounts)
        fun registerFail(msg: String)
    }

    interface Presenter : BasePresenter {
        /**
         * 注册操作
         */
        fun register(id: String, name: String, pwd: String)
        interface OnRegisterCallBack {
            fun registerSuccess(userAccount: Accounts)
            fun registerFail(message: String)
        }
    }

    interface Task {
        fun goRegister(
            id: String,
            name: String,
            pwd: String,
            onRegisterCallBack: Presenter.OnRegisterCallBack
        )
    }

}