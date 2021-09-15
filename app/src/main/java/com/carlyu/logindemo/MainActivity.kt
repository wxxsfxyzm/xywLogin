package com.carlyu.logindemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.carlyu.logindemo.balance.BalanceActivity
import com.carlyu.logindemo.bean.User
import com.carlyu.logindemo.login.LoginActivity
import com.carlyu.logindemo.utils.SPUtil
import com.carlyu.logindemo.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.indeterminateProgressDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extraUserData = intent.getSerializableExtra("user")
        Log.d(
            "MainActivity",
            "extraData is $extraUserData"
        )
        val user = extraUserData as User
        userInfo.text = when {
            extraUserData.studentName != "" -> user.studentName
            else -> "Internal Error"
        }
        errCheck(userInfo.text)
        userBalance.text = when {
            extraUserData.balance.toString() != "" -> user.balance.toPlainString()
            else -> "Internal Error"
        }
        errCheck(userInfo.text)
        remain_deposit_button.setOnClickListener {
            BalanceActivity.startActivity(this, user)
            finish()
        }
        go_get_detail_button.setOnClickListener {
            toast("开发中！")
        }
        exit_login.setOnClickListener {
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