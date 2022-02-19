package com.carlyu.xywlogin.login

import android.content.Context
import android.content.Intent
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.SystemClock.sleep
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.carlyu.xywlogin.R
import com.carlyu.xywlogin.base.BaseActivity
import com.carlyu.xywlogin.common.Constant.ResultEnum.IP_TYPE_ERROR
import com.carlyu.xywlogin.common.Constant.ResultEnum.NET_TYPE_ERROR
import com.carlyu.xywlogin.databinding.ActivityLoginBinding
import com.carlyu.xywlogin.exception.MyException
import com.carlyu.xywlogin.room.AppDatabase
import com.carlyu.xywlogin.room.User
import com.carlyu.xywlogin.room.UserDao
import com.carlyu.xywlogin.utils.ConnectUtils
import com.carlyu.xywlogin.utils.toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.uiThread


class LoginActivity : BaseActivity<ActivityLoginBinding>(),
    LoginContract.View {

    private var loginPresenter: LoginContract.Presenter? = null

    private lateinit var netType: String
    private lateinit var ipType: String
    private var flagNumber: Boolean = false
    private var flagPasswd: Boolean = false
    private var isLoginSuccessful: Boolean = false

    companion object {
        fun startActivity(ctx: Context) {
            val i = Intent(ctx, LoginActivity::class.java)
            ctx.startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  DEBUG Thread
        Thread {
            Log.d(
                "isNetworkAvailable",
                ConnectUtils.isNetworkAvailable(this).toString()
            )
            Log.d(
                "isConnected",
                ConnectUtils.isConnected(this).toString()
            )
            Log.d(
                "NetworkType",
                ConnectUtils.getNetworkType(this)!!
            )
            Log.d(
                "isWiFi",
                ConnectUtils.isWifiNetwork(this).toString()
            )
            Log.d("Context", this.applicationContext.toString())
        }.start()
        // TODO
        //  PreProcess Functions
        //
        // DEBUG Auto Login
        // Interferes UIThread, so we must  use multi-thread with caution
        Thread {
            if (user != null)
                if (user!!.isRememberChecked)
                    if (user!!.isAutoLoginChecked) {
                        runOnUiThread {
                            showLoginDialog(true)
                        }
                        // TODO Temporarily Set Sleep Time
                        //  For Timeout From Java is 10s
                        // sleep(13000)
                        // finishAfterMS(3000)
                    }
        }.start()
    }

    // Already changed buttonType to solve the problem
    // remain just in case
    // actually overrides nothing
/*    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        try {
            super.onRestoreInstanceState(savedInstanceState)
        } catch (e: Exception) {
            Log.d("ExceptionClass:", e.javaClass.toGenericString())
            Log.d(null, "======================")
            e.printStackTrace()
            Log.d(null, "======================")
        }
    }*/

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
        //userDAO = AppDatabase.getInstance(this).userDao()
        // DEBUG Thread
        //  Set Network
        Thread {
            ConnectUtils.setNetwork(this, NetworkCapabilities.TRANSPORT_WIFI)
        }.start()

    }

    override fun initViews() {

        /**
         * Initialize Values
         */
        netType = "CMCC_EDU"
        ipType = "Nine"

        /**
         * Radio Group OnclickListener
         */
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

        /**
         * Hidden Radio Group OnclickListener
         */
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

        /**
         * Two Checkboxes
         */
        // TODO
        // Set RememberMe Checkbox True To Default
        // AutoLogin To False
        doAsync {
            if (user == null) {
                binding.checkboxRememberMe.isChecked = true
                binding.checkboxAutomaticLogin.isChecked = false
            } else if (user!!.isRememberChecked) {
                // DEBUG Choose Checkbox
                binding.radioGroup.check(
                    when (user!!.netType) {
                        "CMCC_EDU" -> binding.radioButtonCmccEdu.id
                        "f-Young" -> binding.radioButtonFYoung.id
                        "NJFU_WiFi" -> binding.radioButtonNjfuWifi.id
                        else -> throw MyException(NET_TYPE_ERROR)
                    }
                )
                if (user!!.netType == "NJFU_WiFi")
                    binding.radioGroupHidden.check(
                        when (user!!.ipType) {
                            "Nine" -> binding.radioButtonNine.id
                            "Five" -> binding.radioButtonFive.id
                            "Lib" -> binding.radioButtonLib.id
                            else -> throw MyException(IP_TYPE_ERROR)
                        }
                    )
                binding.checkboxRememberMe.isChecked = true
                /**
                 * Fill UserInfo if Set in Database
                 */
                uiThread {
                    binding.inputId.setText(user!!.userName)
                    binding.password.setText(user!!.userPasswd)
                }
                if (user!!.isAutoLoginChecked)
                    binding.checkboxAutomaticLogin.isChecked = true
            }
        }


        // Handler For Checkbox RememberMe
        binding.checkboxRememberMe.apply {
            setOnCheckedChangeListener { _, _ ->
                Log.d("checkboxRememberMe", "checked")
            }
            //  if (user != null && user.isRememberChecked)
            //      check(true)
        }
        // DEBUG State Change
        if (isRememberMeChecked()) {
            Log.d("checkboxRememberMe", "checked")
        }

        /**
         * Loading Circle On Pressing Login
         */
        binding.loginBtnLogin.apply {
            textSize = 15F
            setOnClickListener {
                showLoginDialog(false)
                // Update Database
                doAsync {
                    // Log.d("User", userDAO.getUserByUserName(getUserById()).toString())
                    if (isRememberMeChecked() && userDAO.getUserByUserName(getUserById()) == null)
                        userDAO.addUser(
                            User(
                                null,
                                getUserById(),
                                getPwd(),
                                netType,
                                ipType,
                                isRememberMeChecked(),
                                isAutoLoginChecked(),
                            )
                        )
                    if (userDAO.getUserByUserName(getUserById()) != null)
                        userDAO.updateUserByUser(
                            User(
                                userDAO.getUserByUserName(getUserById())!!.userId,
                                getUserById(),
                                getPwd(),
                                netType,
                                ipType,
                                isRememberMeChecked(),
                                isAutoLoginChecked(),
                            )
                        )
                }
                // finishAfterMS(0)
            }
        }
    }

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding
            .inflate(layoutInflater, binding.root, true)
    }

    override val userDAO: UserDao
        get() = AppDatabase.getInstance(this).userDao()

    override val user: User?
        get() = userDAO.getUserById(1)

    private fun userToLogin() {
        // if (checkUserInfo()) {
        loginPresenter?.goLogin(getUserById(), getPwd(), netType, ipType)
        // }
    }

    private fun checkUserInfo(): Boolean {
        if (TextUtils.isEmpty(getUserById())) {
            binding.inputId.requestFocus()
            binding.inputId.error = getString(R.string.userid_cant_null)
            flagNumber = true
            return false
        }
        if (TextUtils.isEmpty(getPwd())) {
            binding.password.requestFocus()
            binding.password.error = getString(R.string.password_cant_null)
            flagPasswd = true
            return false
        }
        return true
    }

    private fun isRememberMeChecked(): Boolean = binding.checkboxRememberMe.isChecked

    private fun isAutoLoginChecked(): Boolean = binding.checkboxAutomaticLogin.isChecked

    private fun showLoginDialog(cancelable: Boolean) {
        indeterminateProgressDialog("正在登录中", "请稍候") {
            setCancelable(cancelable)
            setOnShowListener {
                if (checkUserInfo()) {
                    doAsync {
                        userToLogin()
                        // TODO
                        //  Check If Succeed
                        if (isLoginSuccessful)
                            uiThread {
                                // DEBUG Message
                                toast("Success")
                            }
                    }
                } else {
                    // DEBUG Message
                    if (flagNumber)
                        toast(getString(R.string.userid_cant_null))
                    if (flagPasswd)
                        toast(getString(R.string.password_cant_null))
                }
                it.dismiss()

            }
        }
    }

    override fun setupToolbar() {
        val topAppBar = findViewById<Toolbar>(R.id.toolbar)
        topAppBar.title = getString(R.string.login_xyw)
        topAppBar.subtitle = getString(R.string.app_intro)

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    // Handle favorite icon press
                    toast("Thanks!")
                    true
                }
/*                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    true
                }*/
                else -> false
            }
        }

        // No longer Needed
        // setSupportActionBar(mToolbar)

    }

    private fun finishAfterMS(ms: Long) {
        sleep(ms)
        super.finish()
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
        isLoginSuccessful = true
    }

    override fun loginFail(msg: String) {
        toast(msg)
        isLoginSuccessful = false
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        loginPresenter = presenter
    }

    /**
     * DP 转换 PX 的工具类
     */
    override fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, context.resources.displayMetrics
        ).toInt()
    }

    //用来控制Fragment的显示隐藏
    /*fun showFragment(fragment: Fragment, fm: FragmentManager) {
        //判断当前显示的是否是需要展示的Framgnet，可以省略不必要步骤
        if (currentFragment != fragment) {
            //隐藏当前Fragment
            val transaction = fm.beginTransaction()
            transaction.hide(currentFragment)
            //将fragment替换成目前传入的fragment
            currentFragment = fragment
            //判断当前fragment是否添加进事务中
            if (!fragment.isAdded) {
                //添加显示
                transaction.add(R.id.fl_layout, fragment).show(fragment).commit()
            } else {
                //显示
                transaction.show(fragment).commit()
            }
        }
    }*/

}