package com.carlyu.logindemo.login

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.carlyu.logindemo.MainActivity
import com.carlyu.logindemo.R
import com.carlyu.logindemo.base.BaseActivity
import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.register.RegisterActivity
import com.carlyu.logindemo.utils.SPUtil
import com.carlyu.logindemo.utils.toast
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Roman on 2021/1/22
 */
class LoginActivity : BaseActivity(), LoginContract.View {

    private var loginPresenter: LoginContract.Presenter? = null

    companion object {
        fun startActivity(ctx: Context) {
            val i = Intent(ctx, LoginActivity::class.java)
            ctx.startActivity(i)
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        LoginPresenter(this)
    }

    override fun initViews() {
        login.setOnClickListener {
            userToLogin()
        }
        register.setOnClickListener {
            RegisterActivity.startActivity(this)
        }
    }

    private fun userToLogin() {
        if (checkUserInfo()) {
            loginPresenter?.goLogin(getUserById(), getPwd())
        }
    }

    private fun checkUserInfo(): Boolean {
        if (TextUtils.isEmpty(getUserById())) {
            input_id.requestFocus()
            input_id.error = getString(R.string.userid_cant_null)
            return false
        }
        if (TextUtils.isEmpty(getPwd())) {
            password.requestFocus()
            password.error = getString(R.string.password_cant_null)
            return false
        }
        return true
    }

    override fun setupToolbar() { // 我还没有做ToolBar，要不你来做一个
    }

    /*    override fun getUserById(): Int {
            return if (userid.text.toString() == "")
                0
            else
                try {
                    userid.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    -1
                }
        }*/
    override fun getUserById(): String =
        input_id.text.toString()

    override fun getPwd(): String {
        return password.text.toString()
    }

    override fun loginSuccess(userAccount: Accounts) {
        toast(getString(R.string.login_success))
        SPUtil.saveLogin(true)
        MainActivity.startActivity(this, userAccount.data)
        finish()
    }

    override fun loginFail(msg: String) {
        toast("登录失败，${msg}")
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        loginPresenter = presenter
    }
}