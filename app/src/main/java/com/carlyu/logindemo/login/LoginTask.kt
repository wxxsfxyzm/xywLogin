package com.carlyu.logindemo.login

import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.common.Constant.REQUEST_BASE_URL
import com.carlyu.logindemo.net.APIService
import com.carlyu.logindemo.net.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginTask : LoginContract.Task {

    private var callBack: LoginContract.Presenter.OnLoginCallBack? = null


    override fun login(
        userid: String?,
        password: String?,
        onLoginCallBack: LoginContract.Presenter.OnLoginCallBack
    ) {
        callBack = onLoginCallBack
        val mLogin = RetrofitManager.getService(REQUEST_BASE_URL, APIService.Login::class.java)
        if (userid!!.isNotEmpty() && password!!.isNotEmpty()) {

            val longCall = mLogin.toLogin(userid, password)
            longCall.enqueue(object : Callback<Accounts> {

                override fun onFailure(call: Call<Accounts>, t: Throwable) {
                    callBack?.loginFail("登录失败")
                }

                override fun onResponse(call: Call<Accounts>, response: Response<Accounts>) {

                    val result: Accounts? = response.body()
                    if (result != null && "0" == result.code) {
                        callBack?.loginSuccess(result)
                    } else {
                        callBack?.loginFail(result!!.msg)
                    }
                }
            })
        }
    }
}