package com.carlyu.logindemo.balance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.carlyu.logindemo.R
import com.carlyu.logindemo.base.BaseActivity
import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.bean.User
import java.math.BigDecimal

class BalanceActivity : BaseActivity(), BalanceContract.View {

    private var balancePresenter: BalanceContract.Presenter? = null

    // will be initiated on pressing the chosen button
    private lateinit var balanceValue: BigDecimal

    private val extraUserData = intent.getSerializableExtra("user")
    private val user = extraUserData as User


    companion object {
        fun startActivity(ctx: Context) {
            val i = Intent(ctx, BalanceActivity::class.java)
            ctx.startActivity(i)
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
        TODO("each button in view usage")
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

    private fun checkBalanceInput(): Boolean {
        TODO("Check balance input")
    }

    override fun getUserById(): String = user.userid

    override fun getDepositAmount(): BigDecimal {
        TODO("Not yet implemented")
    }

    override fun depositSuccess(userAccount: Accounts) {
        TODO("Not yet implemented")
    }

    override fun depositFail(msg: String) {
        TODO("Not yet implemented")
    }

    override fun setPresenter(presenter: BalanceContract.Presenter) {
        balancePresenter = presenter
    }
}