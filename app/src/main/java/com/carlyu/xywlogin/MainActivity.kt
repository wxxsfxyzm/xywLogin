package com.carlyu.xywlogin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.carlyu.xywlogin.balance.BalanceActivity
import com.carlyu.xywlogin.bean.User
import com.carlyu.xywlogin.databinding.ActivityMainBinding
import com.carlyu.xywlogin.login.LoginActivity
import com.carlyu.xywlogin.utils.SPUtil
import com.carlyu.xywlogin.utils.toast
import org.jetbrains.anko.indeterminateProgressDialog

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
        val user = extraUserData as User
        binding.userInfo.text = when {
            extraUserData.studentName != "" -> user.studentName
            else -> "Internal Error"
        }
        errCheck(binding.userInfo.text)
        binding.userBalance.text = when {
            extraUserData.balance.toString() != "" -> user.balance.toPlainString()
            else -> "Internal Error"
        }
        errCheck(binding.userInfo.text)
        binding.remainDepositButton.setOnClickListener {
            BalanceActivity.startActivity(this, user)
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
        binding.exitLogin.setOnClickListener {
            indeterminateProgressDialog("退出登录中", "请稍候")
            SPUtil.saveLogin(false)
            LoginActivity.startActivity(this)
            toast("退出登录成功！")
            finish()
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