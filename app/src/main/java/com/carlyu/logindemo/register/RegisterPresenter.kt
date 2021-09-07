package com.carlyu.logindemo.register

import com.carlyu.logindemo.bean.Accounts
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class RegisterPresenter(val view: RegisterContract.View) : RegisterContract.Presenter {
    private var registerTask: RegisterTask? = null

    init {
        view.setPresenter(this)
        registerTask = RegisterTask()
    }

    override fun register(id: String, name: String, pwd: String) {
        doAsync {
            registerTask?.goRegister(id, name, pwd, object : RegisterContract.Presenter.OnRegisterCallBack {

                override fun registerSuccess(userAccount: Accounts) {
                    uiThread {
                        view.registerSuccess(userAccount)
                    }
                }

                override fun registerFail(message: String) {
                    uiThread {
                        view.registerFail(message)
                    }
                }

            })
        }
    }

    override fun start() {

    }
}