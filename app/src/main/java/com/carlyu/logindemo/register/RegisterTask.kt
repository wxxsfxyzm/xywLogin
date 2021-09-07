package com.carlyu.logindemo.register

import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.common.Constant
import com.carlyu.logindemo.net.APIService
import com.carlyu.logindemo.net.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterTask : RegisterContract.Task {
    private var callback: RegisterContract.Presenter.OnRegisterCallBack? = null

    override fun goRegister(
        id: String,
        name: String,
        pwd: String,
        onRegisterCallBack: RegisterContract.Presenter.OnRegisterCallBack
    ) {
        callback = onRegisterCallBack

        val registerService =
            RetrofitManager.getService(Constant.REQUEST_BASE_URL, APIService.Register::class.java)
        val registerCallBack = registerService.toRegister(id, name, pwd)
        registerCallBack.enqueue(object : Callback<Accounts> {

            override fun onFailure(call: Call<Accounts>, t: Throwable) {
                callback?.registerFail("注册失败")
            }

            override fun onResponse(call: Call<Accounts>, response: Response<Accounts>) {
                val result: Accounts? = response.body()
                if (result != null && "0" == result.code) {
                    callback?.registerSuccess(result)
                } else {
                    callback?.registerFail(result!!.msg)
                }
            }
        })
    }
}