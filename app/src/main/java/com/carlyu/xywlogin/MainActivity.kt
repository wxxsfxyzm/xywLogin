package com.carlyu.xywlogin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.carlyu.xywlogin.bean.User
import com.carlyu.xywlogin.databinding.ActivityMainBinding
import com.carlyu.xywlogin.utils.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var clickCount = 0
        val extraUserData = intent.getSerializableExtra("user")
        Log.d(
            "MainActivity",
            "extraData is $extraUserData"
        )
        errCheck(binding.userInfo.text)
        binding.remainDepositButton.setOnClickListener {
            //BalanceActivity.startActivity(this, user)
            //若放弃充值可以返回首页，没必要finish
            //finish()
        }
        binding.goGetDetailButton.setOnClickListener {
            if (clickCount == 3) {
                toast("别戳了")
            } else {
                clickCount++
                toast("开发中！")
            }
        }
    }

    private fun errCheck(any: Any) {
        if (any == "Internal Error") {
            toast("$any Internal Error")
            Log.w("MainActivityErrCheck", "$any Error")
        }
    }

    companion object {
        fun startActivity(ctx: Context, user: User) {
            val intent = Intent(ctx, MainActivity::class.java)

            intent.putExtra("user", user)
            ctx.startActivity(intent)
        }

        fun startActivity(ctx: Context, userid: String) {
            val intent = Intent(ctx, MainActivity::class.java)

            intent.putExtra("userid", userid)
            ctx.startActivity(intent)
        }

        fun startActivity(ctx: Context) {
            val intent = Intent(ctx, MainActivity::class.java)
            ctx.startActivity(intent)
        }
    }
}