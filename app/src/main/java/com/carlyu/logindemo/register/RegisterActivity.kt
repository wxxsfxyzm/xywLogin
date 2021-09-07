package com.carlyu.logindemo.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.carlyu.logindemo.R
import com.carlyu.logindemo.base.BaseActivity
import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.utils.toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), RegisterContract.View {
    private var registerPresenter: RegisterContract.Presenter? = null

    companion object {
        fun startActivity(ctx: Context) {
            val intent = Intent(ctx, RegisterActivity::class.java)
            ctx.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int {
        return R.layout.activity_register
    }

    override fun initData() {
        RegisterPresenter(this)
    }

    override fun initViews() {
        btn_register.setOnClickListener {
            goRegister()
        }
    }

    private fun goRegister() {
        if (checkRegister()) {
            registerPresenter!!.register(getUserById(), getUserName(), getPwd())
        }
    }

    private fun checkRegister(): Boolean {
        if (TextUtils.isEmpty(getUserById())) {
            input_register_id.requestFocus()
            input_register_id.error = getString(R.string.userid_cant_null)
            return false
        }
        if (TextUtils.isEmpty(getUserName())) {
            input_name.requestFocus()
            input_name.error = getString(R.string.username_cant_null)
            return false
        }
        if (TextUtils.isEmpty(getPwd())) {
            input_pwd.requestFocus()
            input_pwd.error = getString(R.string.password_cant_null)
            return false
        }
        if (getPwd() != getConfirmPwd()) {
            input_confirm_pwd.requestFocus()
            input_confirm_pwd.error = getString(R.string.pwd_not_confirm)
            return false
        }
        return true
    }

    override fun setupToolbar() {

    }

    override fun getUserById(): String =
        input_register_id.text.toString()


    override fun getUserName(): String {
        return input_name.text.toString()
    }

    override fun getPwd(): String {
        return input_pwd.text.toString()
    }

    override fun getConfirmPwd(): String {
        return input_confirm_pwd.text.toString()
    }

    override fun registerSuccess(userAccount: Accounts) {
        toast("注册成功!\n${userAccount.data.userid}${userAccount.data.username}${userAccount.data.password}")
        finish()
    }

    override fun registerFail(msg: String) {
        toast("注册失败,${msg}!")
    }

    override fun setPresenter(presenter: RegisterContract.Presenter) {
        registerPresenter = presenter
    }
}