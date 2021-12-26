package com.carlyu.xywlogin.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.carlyu.xywlogin.R
import com.carlyu.xywlogin.base.BaseActivity
import com.carlyu.xywlogin.databinding.ActivityLoginBinding
import org.jetbrains.anko.indeterminateProgressDialog


class LoginActivity : BaseActivity<ActivityLoginBinding>(), LoginContract.View {

    private var loginPresenter: LoginContract.Presenter? = null

    private lateinit var netType: String

    private lateinit var ipType: String

    companion object {
        fun startActivity(ctx: Context) {
            val i = Intent(ctx, LoginActivity::class.java)
            ctx.startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Already changed buttonType to solve the problem
    // remain just in case
    // actually overrides nothing
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

    // no longer needed
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
        /**
         * Initialize Values
         */
        netType = "CMCC_EDU"
        ipType = "Nine"

        // Radio Group OnclickListener
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonCmccEdu.id -> kotlin.run {
                    netType = "CMCC_EDU"
                    binding.textViewId.visibility = View.GONE
                    binding.radioGroupHidden.visibility = View.GONE
                    binding.secondBlockRelativeLayout
                        .setPadding(
                            binding.secondBlockRelativeLayout.paddingLeft,
                            dp2px(this, 155F),
                            binding.secondBlockRelativeLayout.paddingRight,
                            binding.secondBlockRelativeLayout.paddingBottom
                        )
                }
                binding.radioButtonFYoung.id -> kotlin.run {
                    netType = "f-Young"
                    binding.textViewId.visibility = View.GONE
                    binding.radioGroupHidden.visibility = View.GONE
                    binding.secondBlockRelativeLayout
                        .setPadding(
                            binding.secondBlockRelativeLayout.paddingLeft,
                            dp2px(this, 155F),
                            binding.secondBlockRelativeLayout.paddingRight,
                            binding.secondBlockRelativeLayout.paddingBottom
                        )
                }
                binding.radioButtonNjfuWifi.id -> kotlin.run {
                    netType = "NJFU_WiFi"
                    binding.textViewId.visibility = View.VISIBLE
                    binding.radioGroupHidden.visibility = View.VISIBLE
                    binding.secondBlockRelativeLayout
                        .setPadding(
                            binding.secondBlockRelativeLayout.paddingLeft,
                            dp2px(this, 5F),
                            binding.secondBlockRelativeLayout.paddingRight,
                            binding.secondBlockRelativeLayout.paddingBottom
                        )

                }
                else -> kotlin.run {
                    netType = "ERROR"
                    Log.d("RadioGroupCheckId", "checkIdError")
                }

            }
        }

        // Hidden Radio Group OnclickListener
        binding.radioGroupHidden.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonNine.id -> ipType = "Nine"
                binding.radioButtonLib.id -> ipType = "Lib"
                binding.radioButtonFive.id -> ipType = "Five"
                else -> kotlin.run {
                    ipType = "ERROR"
                    Log.d("RadioGroupHiddenCheckId", "checkIdError")

                }
            }

        }

        // Loading Circle On Pressing Login
        binding.loginBtnLogin.apply {
            textSize = 15F
            setOnClickListener {
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
        }
    }

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater, binding.root, true)
    }

    private fun userToLogin() {
        if (checkUserInfo()) {
            loginPresenter?.goLogin(getUserById(), getPwd(), netType, ipType)
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
        mToolbar.title = "登录校园网"

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

    override fun getPwd(): String =
        binding.password.text.toString()

    override fun loginSuccess() {

    }

    override fun loginFail(msg: String) {

    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        loginPresenter = presenter
    }


    override fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, context.resources.displayMetrics
        ).toInt()
    }
}