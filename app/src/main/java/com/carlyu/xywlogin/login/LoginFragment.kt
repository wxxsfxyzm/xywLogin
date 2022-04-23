package com.carlyu.xywlogin.login

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import com.carlyu.xywlogin.R
import com.carlyu.xywlogin.base.BaseFragment
import com.carlyu.xywlogin.common.Constant
import com.carlyu.xywlogin.common.Constant.S
import com.carlyu.xywlogin.common.Constant.buildVersion
import com.carlyu.xywlogin.databinding.FragmentLoginBinding
import com.carlyu.xywlogin.exception.MyException
import com.carlyu.xywlogin.room.AppDatabase
import com.carlyu.xywlogin.room.User
import com.carlyu.xywlogin.room.UserDao
import com.carlyu.xywlogin.utils.dp2px
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.uiThread


class LoginFragment : BaseFragment<FragmentLoginBinding>(),
    LoginContract.View {

    private var loginPresenter: LoginContract.Presenter? = null

    private lateinit var netType: String
    private lateinit var ipType: String
    private var flagNumber: Boolean = false
    private var flagPasswd: Boolean = false
    private var isLoginSuccessful: Boolean = false

    /*    override fun getLayout(): Int {
            TODO("Not yet implemented")
        }*/


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Thread {
            if (user != null)
                if (user!!.isRememberChecked)
                    if (user!!.isAutoLoginChecked) {
                        requireActivity().runOnUiThread {
                            showLoginDialog(true)
                        }
                        // TODO Temporarily Set Sleep Time
                        //  For Timeout From Java is 10s
                        // sleep(13000)
                        // finishAfterMS(3000)
                    }
        }.start()
    }

/*    // DEBUG Duplicate for test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            if (user != null)
                if (user!!.isRememberChecked)
                    if (user!!.isAutoLoginChecked) {
                        requireActivity().runOnUiThread {
                            showLoginDialog(true)
                        }
                        // TODO Temporarily Set Sleep Time
                        //  For Timeout From Java is 10s
                        // sleep(13000)
                        // finishAfterMS(3000)
                    }
        }.start()
    }*/

    override fun initData() {
        LoginPresenter(this)
    }

    override fun initViews() {
        if (buildVersion < S) {
            binding.usernameLayout.apply {
                boxStrokeColor = getColor(context, R.color.colorAccent)
                setHintTextAppearance(R.style.CustomAppTheme_textInputLayout)
            }
            binding.passwordLayout.apply {
                boxStrokeColor = getColor(context, R.color.colorAccent)
                setHintTextAppearance(R.style.CustomAppTheme_textInputLayout)
            }
        }


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
                            dp2px(requireContext(), 155F),
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
                            dp2px(requireContext(), 155F),
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
                            dp2px(requireContext(), 5F),
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
                        else -> throw MyException(Constant.ResultEnum.NET_TYPE_ERROR)
                    }
                )
                if (user!!.netType == "NJFU_WiFi")
                    binding.radioGroupHidden.check(
                        when (user!!.ipType) {
                            "Nine" -> binding.radioButtonNine.id
                            "Five" -> binding.radioButtonFive.id
                            "Lib" -> binding.radioButtonLib.id
                            else -> throw MyException(Constant.ResultEnum.IP_TYPE_ERROR)
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
            if (Build.VERSION.SDK_INT >= S)
                setTextAppearance(R.style.Material3Button)
            else {
                setBackgroundColor(getColor(context, R.color.register_button))
            }
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

    override val userDAO: UserDao
        get() = AppDatabase.getInstance(requireContext()).userDao()

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

        activity?.indeterminateProgressDialog("正在登录中", "请稍候") {
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
                                Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                            }
                    }
                } else {
                    // DEBUG Message
                    if (flagNumber)
                        Toast.makeText(activity, getString(R.string.userid_cant_null), Toast.LENGTH_LONG).show()

                    if (flagPasswd)
                        Toast.makeText(activity, getString(R.string.password_cant_null), Toast.LENGTH_LONG).show();
                }
                it.dismiss()

            }
        }
    }

    override fun getUserById(): String =
        binding.inputId.text.toString()

    override fun getPwd(): String =
        binding.password.text.toString()

    override fun loginSuccess() {
        isLoginSuccessful = true
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        loginPresenter = presenter
    }

    override fun loginFail(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        isLoginSuccessful = false
    }


}
