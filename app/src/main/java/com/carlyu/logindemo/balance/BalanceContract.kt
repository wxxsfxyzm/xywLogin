package com.carlyu.logindemo.balance

import com.carlyu.logindemo.base.BasePresenter
import com.carlyu.logindemo.base.BaseView
import com.carlyu.logindemo.bean.Accounts
import java.math.BigDecimal

interface BalanceContract {
    interface View : BaseView<Presenter> {
        /**
         * 获取用户id
         */
        //fun getUserById(): String

        /**
         *
         */
        //fun getDepositAmount(): BigDecimal

        /**
         * 充值成功
         */

        fun depositSuccess(userAccount: Accounts)

        /**
         * 充值失败
         */
        fun depositFail(msg: String)
    }

    interface Presenter : BasePresenter {
        /**
         * 开始充值操作
         */
        fun goDepositOperation(userid: String, balance: BigDecimal)

        interface OnOperationCallback {
            // 回传用户信息，用用户信息来刷新MainActivity
            fun operationSuccess(userAccount: Accounts)
            fun operationFail(message: String)
        }
    }

    interface Task {
        // fun
        fun depositOperation(
            userid: String?,
            balance: BigDecimal?,
            onOperationCallback: Presenter.OnOperationCallback
        )
    }
}