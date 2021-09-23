package com.carlyu.logindemo.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.carlyu.logindemo.MainActivity
import com.carlyu.logindemo.R
import com.carlyu.logindemo.base.BaseActivity
import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.databinding.ActivityLoginBinding
import com.carlyu.logindemo.register.RegisterActivity
import com.carlyu.logindemo.utils.SPUtil
import com.carlyu.logindemo.utils.toast
import org.jetbrains.anko.indeterminateProgressDialog


open class LoginActivity : BaseActivity<ActivityLoginBinding>(), LoginContract.View {

    private var loginPresenter: LoginContract.Presenter? = null

    companion object {
        fun startActivity(ctx: Context) {
            val i = Intent(ctx, LoginActivity::class.java)
            ctx.startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        try {
            super.onRestoreInstanceState(savedInstanceState)
        } catch (e: Exception) {
            Log.d("ExceptionClass:", e.javaClass.toGenericString())
            Log.d(null, "======================")
            e.printStackTrace()
            Log.d(null, "======================")
        }
    }

/*    override fun getLayout(): Int {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        *//*return R.layout.activity_login*//*
        Log.d("R.layout.activity_login", R.layout.activity_login.toString())
        Log.d("binding.root.sourceLayoutResId", binding.root.sourceLayoutResId.toString())
        return binding.root.sourceLayoutResId
    }*/

    override fun initData() {
        LoginPresenter(this)

    }

    override fun initViews() {
        binding.login.setOnClickListener {
            indeterminateProgressDialog("正在登录中", "请稍候") {
                setCancelable(false)
                setOnShowListener {
                    userToLogin()
                    kotlin.run {
                        Thread.sleep(2000)
                        it.cancel()
                    }
                }
            }

        }
        binding.loginBtnRegister.setOnClickListener {
            RegisterActivity.startActivity(this)
        }
    }

    override fun getViewBinding(): ActivityLoginBinding? {
        return ActivityLoginBinding.inflate(layoutInflater, binding.root, true)
    }

    private fun userToLogin() {
        if (checkUserInfo()) {
            loginPresenter?.goLogin(getUserById(), getPwd())
        }
    }

    private fun checkUserInfo(): Boolean {
        if (TextUtils.isEmpty(getUserById())) {
            binding.inputId.requestFocus()
            binding.inputId.error = getString(R.string.userid_cant_null)
            return false
        }
        if (TextUtils.isEmpty(getPwd())) {
            binding.password.requestFocus()
            binding.password.error = getString(R.string.password_cant_null)
            return false
        }
        return true
    }

    override fun setupToolbar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        mToolbar.title = "登录"
        setSupportActionBar(mToolbar)

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
        binding.inputId.text.toString()

    override fun getPwd(): String {
        return binding.password.text.toString()
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