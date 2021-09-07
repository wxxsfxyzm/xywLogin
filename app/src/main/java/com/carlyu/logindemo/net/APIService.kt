package com.carlyu.logindemo.net

import com.carlyu.logindemo.bean.Accounts
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.math.BigDecimal

/**
 * Created by Roman on 2021/1/24
 */
class APIService {

    /**
     * 注册
     */
    interface Register {
        @FormUrlEncoded
        @POST("register")
        fun toRegister(
            @Field("userid") userid: String,
            @Field("username") username: String,
            @Field("password") password: String
        ): Call<Accounts>
    }

    /**
     * 登录
     */
    interface Login {
        @FormUrlEncoded
        @POST("login")
        fun toLogin(
            @Field("userid") userid: String,
            @Field("password") password: String
        ): Call<Accounts>
    }

    /**
     * 充值
     */
    interface Deposit {
        @FormUrlEncoded
        @POST("deposit")
        fun toDeposit(
            @Field("userid") userid: String,
            @Field("balance") balance: BigDecimal
        ): Call<Accounts>
    }
}
