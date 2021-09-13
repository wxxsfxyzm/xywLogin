package com.carlyu.logindemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.carlyu.logindemo.balance.BalanceActivity
import com.carlyu.logindemo.bean.User
import com.carlyu.logindemo.utils.toast
import kotlinx.android.synthetic.main.activity_main.*

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
            extraUserData.username != "" -> user.username
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