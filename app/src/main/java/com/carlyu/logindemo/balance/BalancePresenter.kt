package com.carlyu.logindemo.balance

class BalancePresenter(val view: BalanceContract.View) :
    BalanceContract.Presenter, BalanceContract.Presenter.OnOperationCallback {
    private var mTask: BalanceContract.Task? = null

    init {
        view.setPresenter(this)
        mTask = BalanceTask()
    }
}