package com.carlyu.logindemo.balance

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.carlyu.logindemo.R
import com.carlyu.logindemo.base.BaseActivity
import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.bean.User
import com.carlyu.logindemo.utils.toast
import kotlinx.android.synthetic.main.activity_balance.*
import java.math.BigDecimal

class BalanceActivity : BaseActivity(), BalanceContract.View {

    private var balancePresenter: BalanceContract.Presenter? = null

    // will be initiated on pressing the chosen button
    private lateinit var balanceValue: BigDecimal

    private val extraUserData = intent.getSerializableExtra("user")
    private val user = extraUserData as User


    companion object {
        fun startActivity(ctx: Context, user: User) {
            val intent = Intent(ctx, BalanceActivity::class.java)

            intent.putExtra("user", user)
            ctx.startActivity(intent)
        }

        fun startActivity(ctx: Context) {
            val intent = Intent(ctx, BalanceActivity::class.java)
            ctx.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setupToolbar() {
    }

    override fun getLayout(): Int {
        return R.layout.activity_balance
    }

    override fun initData() {
        BalancePresenter(this)
    }

    override fun initViews() {
        ten_yuan.setOnClickListener {
            balanceValue = 10.00.toBigDecimal()
            balanceToDeposit()
        }
        thirty_yuan.setOnClickListener {
            balanceValue = 30.00.toBigDecimal()
            balanceToDeposit()
        }
        fifty_yuan.setOnClickListener {
            balanceValue = 50.00.toBigDecimal()
            balanceToDeposit()
        }
        hundred_yuan.setOnClickListener {
            balanceValue = 100.00.toBigDecimal()
            balanceToDeposit()
        }
    }

    private fun balanceToDeposit() {
        if (checkBalanceInput()) {
            balancePresenter?.goDepositOperation(getUserById(), balanceValue)
            TODO(
                "userid from previous activity" +
                        "balance from textInput input" +
                        "Nothing inside body"
            )
        }
    }

    private fun showCoverDialog(ctx: Context) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("提示")
        builder.setMessage("你选择了")
    }

    private fun checkBalanceInput(): Boolean {
        TODO("Check balance input")
    }

    override fun getUserById(): String = user.userid

    override fun getDepositAmount(): BigDecimal {
        TODO("Not yet implemented")
    }

    override fun depositSuccess(userAccount: Accounts) {
        //TODO("Not yet implemented")
        toast("充值+$balanceValue+成功")
    }

    override fun depositFail(msg: String) {
        TODO("Not yet implemented")
    }

    override fun setPresenter(presenter: BalanceContract.Presenter) {
        balancePresenter = presenter
    }
}