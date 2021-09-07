package com.carlyu.logindemo.login

import com.carlyu.logindemo.bean.Accounts
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class LoginPresenter(val view: LoginContract.View) :
    LoginContract.Presenter, LoginContract.Presenter.OnLoginCallBack {
    private var mTask: LoginContract.Task? = null

    init {
        view.setPresenter(this)
        mTask = LoginTask()
    }

    override fun goLogin(userid: String, password: String) {
        doAsync {
            mTask?.login(userid, password, object : LoginContract.Presenter.OnLoginCallBack {

                override fun loginSuccess(userAccount: Accounts) {
                    uiThread {
                        view.loginSuccess(userAccount)
                    }
                }

                override fun loginFail(message: String) {
                    uiThread {
                        view.loginFail(message)
                    }
                }

            })
        }
    }

    override fun start() {

    }

    override fun loginSuccess(userAccount: Accounts) {
        view.loginSuccess(userAccount)
    }

    override fun loginFail(message: String) {
        view.loginFail(message)
    }
}