package com.carlyu.xywlogin.net

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Roman on 2021/1/24
 */
class APIService {


    /**
     * 登录
     */
    interface Login {
        @FormUrlEncoded
        @POST("a70.htm/")
        fun toLogin(
            @Field("DDDDD") userid: String,
            @Field("upass") password: String,
            @Field("R1") R1: String,
            @Field("R3") R3: String,
            @Field("R6") R6: String,
            @Field("para") para: String,
            @Field("0MKKey") Key: String
        ): Call<Void>
    }

}
