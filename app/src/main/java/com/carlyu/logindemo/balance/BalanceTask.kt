package com.carlyu.logindemo.balance

import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.common.Constant.REQUEST_DEPOSIT_URL
import com.carlyu.logindemo.net.APIService
import com.carlyu.logindemo.net.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class BalanceTask : BalanceContract.Task {

    private var callBack: BalanceContract.Presenter.OnOperationCallback? = null

    override fun depositOperation(
        userid: String?,
        balance: BigDecimal?,
        onOperationCallback: BalanceContract.Presenter.OnOperationCallback
    ) {
        callBack = onOperationCallback
        val mDeposit = RetrofitManager.getService(REQUEST_DEPOSIT_URL, APIService.Deposit::class.java)
        if (userid!!.isNotEmpty() && balance!!.toPlainString().isNotEmpty()) {
            val longCall = mDeposit.toDeposit(userid, balance)
            longCall.enqueue(object : Callback<Accounts> {
                override fun onFailure(call: Call<Accounts>, t: Throwable) {
                    callBack?.operationFail("充值失败")
                }

                override fun onResponse(call: Call<Accounts>, response: Response<Accounts>) {

                    val result: Accounts? = response.body()
                    if (result != null && result.code == "0") {
                        callBack?.operationSuccess(result)
                    } else {
                        callBack?.operationFail(result!!.msg)
                    }
                }
            })
        }
    }
}