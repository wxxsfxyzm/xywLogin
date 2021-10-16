package com.carlyu.xywlogin.login

import org.jetbrains.anko.doAsync


class LoginPresenter(private val view: LoginContract.View) :
    LoginContract.Presenter {
    private var mTask: LoginContract.Task? = null

    init {
        view.setPresenter(this)
        mTask = LoginTask()
    }

    override fun goLogin(
        userid: String,
        password: String,
        netType: String
    ) {
        doAsync {
            mTask?.login(
                userid, password, "0", "0", "0", "00", "123456", netType, object : LoginContract.Presenter.OnLoginCallBack {
                    override fun loginSuccess() {

                    }

                    override fun loginFail(message: String) {

                    }
                })
        }
    }

    override fun start() {

    }

}