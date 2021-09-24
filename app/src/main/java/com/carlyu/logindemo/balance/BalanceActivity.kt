package com.carlyu.logindemo.balance

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.carlyu.logindemo.MainActivity
import com.carlyu.logindemo.R
import com.carlyu.logindemo.base.BaseActivity
import com.carlyu.logindemo.bean.Accounts
import com.carlyu.logindemo.bean.User
import com.carlyu.logindemo.databinding.ActivityBalanceBinding
import com.carlyu.logindemo.utils.toast
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog
import java.math.BigDecimal


class BalanceActivity : BaseActivity<ActivityBalanceBinding>(), BalanceContract.View {

    private var balancePresenter: BalanceContract.Presenter? = null

    // will be initiated on pressing the chosen button
    private lateinit var balanceValue: BigDecimal

    // initiate ViewBinding as global variable immediately
    // no longer needed
    // private val binding = ActivityBalanceBinding.inflate(layoutInflater)

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
        val extraUserData = intent.getSerializableExtra("user")
        val user = extraUserData as User

        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        mToolbar.title = user.studentName
        mToolbar.subtitle = user.studentId
        mToolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24)

        setSupportActionBar(mToolbar)

        mToolbar.setNavigationOnClickListener {
            finish()
        }
    }

//    override fun getLayout(): Int = binding.root.sourceLayoutResId
/*    {
        return com.carlyu.logindemo.R.layout.activity_balance
    }*/

    override fun initData() {
        BalancePresenter(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews() {
        //initiate userData from intent
        val extraUserData = intent.getSerializableExtra("user")
        val user = extraUserData as User

        binding.yourMoneyRemaining.apply {
            text = getText(R.string.your_money_remaining)
            textSize = 25F
        }

        binding.remainValue.text = user.balance.toPlainString() + "元"

        binding.tenYuan.setOnClickListener {
            balanceValue = 10.00.toBigDecimal()
            showAlertDialog(user)
        }
        binding.thirtyYuan.setOnClickListener {
            balanceValue = 30.00.toBigDecimal()
            showAlertDialog(user)
        }
        binding.fiftyYuan.setOnClickListener {
            balanceValue = 50.00.toBigDecimal()
            showAlertDialog(user)
        }
        binding.hundredYuan.setOnClickListener {
            balanceValue = 100.00.toBigDecimal()
            showAlertDialog(user)
        }
    }

    private fun balanceToDeposit(user: User) {
        if (checkBalanceInput()) {
            balancePresenter?.goDepositOperation(user.studentId, balanceValue)
        }
    }

    override fun getViewBinding(): ActivityBalanceBinding {
        return ActivityBalanceBinding.inflate(layoutInflater)
    }

    /**显示充值提示框**/
    //@SuppressLint("ResourceAsColor")
    private fun showAlertDialog(user: User) {
        alert(
            "你真的要充值${balanceValue}元吗？",
            "尊敬的${user.studentName}"
        ) {
            positiveButton("确认") {
                Log.i("", "确认充值操作")
                //toast("正在充值")
                indeterminateProgressDialog("正在充值中", "请稍候")
                balanceToDeposit(user)
            }
            negativeButton("我再想想") {
                Log.i("", "取消充值操作")
                toast("您取消了充值！")
            }
        }.show().apply {
            // toast(resources.configuration.uiMode.toString())
            if (isNightMode()) {
                getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.CYAN)
                getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.CYAN)
            } else {
                getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@BalanceActivity, R.color.colorAccent))
                getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@BalanceActivity, R.color.colorAccent))
            }
        }
    }

    private fun checkBalanceInput(): Boolean = when {
        balanceValue.toPlainString() != "" -> true
        else -> false
    }

    /**
     * 判断当前是否深色模式
     *
     * @return 深色模式返回 true，否则返回false
     */
    private fun isNightMode(): Boolean =
        when (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }

    override fun depositSuccess(userAccount: Accounts) {
        toast("充值 $balanceValue 元成功")
        MainActivity.startActivity(this, userAccount.data)
        finish()
    }

    override fun depositFail(msg: String) {
        toast("充值失败！")
    }

    override fun setPresenter(presenter: BalanceContract.Presenter) {
        balancePresenter = presenter
    }

}