package com.carlyu.logindemo.balance

import com.carlyu.logindemo.bean.Accounts
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.math.BigDecimal

class BalancePresenter(val view: BalanceContract.View) :
    BalanceContract.Presenter, BalanceContract.Presenter.OnOperationCallback {
    private var mTask: BalanceContract.Task? = null

    init {
        view.setPresenter(this)
        mTask = BalanceTask()

    }

    override fun goDepositOperation(userid: String, balance: BigDecimal) {
        doAsync {
            mTask?.depositOperation(userid, balance, object : BalanceContract.Presenter.OnOperationCallback {
                override fun operationSuccess(userAccount: Accounts) {
                    uiThread {
                        view.depositSuccess(userAccount)
                    }
                }

                override fun operationFail(message: String) {
                    uiThread {
                        view.depositFail(message)
                    }
                }
            })
        }
    }

    override fun start() {}

    override fun operationSuccess(userAccount: Accounts) {
        view.depositSuccess(userAccount)
    }

    override fun operationFail(message: String) {
        view.depositFail(message)
    }
    
}