package com.carlyu.logindemo.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.carlyu.logindemo.R
import com.carlyu.logindemo.base.BaseActivity
import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.databinding.ActivityRegisterBinding
import com.carlyu.logindemo.utils.toast


class RegisterActivity : BaseActivity(), RegisterContract.View {

    private var registerPresenter: RegisterContract.Presenter? = null

    private val binding = ActivityRegisterBinding.inflate(layoutInflater)

    companion object {
        fun startActivity(ctx: Context) {
            val intent = Intent(ctx, RegisterActivity::class.java)
            ctx.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int = binding.root.sourceLayoutResId
/*    {
        return R.layout.activity_register
    }*/

    override fun initData() {
        RegisterPresenter(this)
    }

    override fun initViews() {
        binding.btnRegister.setOnClickListener {
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
            binding.inputRegisterId.requestFocus()
            binding.inputRegisterId.error = getString(R.string.userid_cant_null)
            return false
        }
        if (TextUtils.isEmpty(getUserName())) {
            binding.inputName.requestFocus()
            binding.inputName.error = getString(R.string.username_cant_null)
            return false
        }
        if (TextUtils.isEmpty(getPwd())) {
            binding.inputPwd.requestFocus()
            binding.inputPwd.error = getString(R.string.password_cant_null)
            return false
        }
        if (getPwd() != getConfirmPwd()) {
            binding.inputConfirmPwd.requestFocus()
            binding.inputConfirmPwd.error = getString(R.string.pwd_not_confirm)
            return false
        }
        return true
    }

    override fun setupToolbar() {

    }

    override fun getUserById(): String =
        binding.inputRegisterId.text.toString()


    override fun getUserName(): String =
        binding.inputName.text.toString()


    override fun getPwd(): String =
        binding.inputPwd.text.toString()

    override fun getConfirmPwd(): String =
        binding.inputConfirmPwd.text.toString()


    override fun registerSuccess(userAccount: Accounts) {
        toast("注册成功!\n${userAccount.data.studentId}${userAccount.data.studentName}${userAccount.data.password}")
        finish()
    }

    override fun registerFail(msg: String) {
        toast("注册失败,${msg}!")
    }

    override fun setPresenter(presenter: RegisterContract.Presenter) {
        registerPresenter = presenter
    }
}