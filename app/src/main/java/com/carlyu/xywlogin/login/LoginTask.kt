package com.carlyu.xywlogin.login

import android.util.Log
import com.carlyu.xywlogin.common.Constant
import com.carlyu.xywlogin.common.Constant.CMCC_URL
import com.carlyu.xywlogin.common.Constant.FYOUNG_URL
import com.carlyu.xywlogin.common.Constant.NJFU_WIFI_LIB_FIVE
import com.carlyu.xywlogin.common.Constant.NJFU_WIFI_NINE
import com.carlyu.xywlogin.exception.MyException
import com.carlyu.xywlogin.net.APIService
import com.carlyu.xywlogin.net.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginTask : LoginContract.Task {

    override fun login(
        userid: String?,
        password: String?,
        R1: String,
        R3: String,
        R6: String,
        para: String,
        Key: String,
        netType: String,
        ipType: String,
        onLoginCallBack: LoginContract.Presenter.OnLoginCallBack
    ) {
        val baseURL: String = when (netType) {
            "CMCC_EDU" -> CMCC_URL
            "f-Young" -> FYOUNG_URL
            "NJFU-WiFi" -> when (ipType) {
                "Nine" -> NJFU_WIFI_NINE
                "Lib" -> NJFU_WIFI_LIB_FIVE
                "Five" -> NJFU_WIFI_LIB_FIVE
                else -> throw MyException(Constant.ResultEnum.INTERNAL_ERROR)
            }
            else -> throw MyException(Constant.ResultEnum.INTERNAL_ERROR)
        }
        val mLogin = RetrofitManager.getService(baseURL, APIService.Login::class.java)
        Log.d("LoginTask", "w$userid,$password,$R1,$R3,$R6,$para,$Key")
        //mLogin.toLogin(userid!!, password!!, R1, R3, R6, para, Key)
        val longCall = mLogin.toLogin("w" + userid!!, password!!, R1, R3, R6, para, Key)
        longCall.enqueue(object : Callback<Void> {


            override fun onFailure(call: Call<Void>, t: Throwable) {
                onLoginCallBack.loginFail("登录失败")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                val result: Void? = response.body()
                Log.d("response", "$result")
/*                    if (result != null && "0" == result.code) {
                    onLoginCallBack.loginSuccess(result)
                } else {
                    onLoginCallBack.loginFail(result!!.msg)
                }*/
            }
        })

    }
}