package com.carlyu.xywlogin.login

import android.content.Context
import android.content.Intent
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.SystemClock.sleep
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.carlyu.xywlogin.R
import com.carlyu.xywlogin.base.BaseActivity
import com.carlyu.xywlogin.common.Constant.S
import com.carlyu.xywlogin.common.Constant.buildVersion
import com.carlyu.xywlogin.databinding.ActivityLoginBinding
import com.carlyu.xywlogin.utils.ConnectUtils
import com.carlyu.xywlogin.utils.toast
import com.google.android.material.navigation.NavigationBarView


class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val loginFragment = LoginFragment()

    private var currentFragment = loginFragment

    companion object {
        fun startActivity(ctx: Context) {
            val i = Intent(ctx, LoginActivity::class.java)
            ctx.startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable Dynamic Coloring On Android S
        if (buildVersion >= S)
            setTheme(R.style.Theme_LoginDemo)
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
            Log.d("ThemeOldAppId", R.style.Theme_OldApp.toString())
            Log.d("ThemeLoginDemoId", R.style.Theme_LoginDemo.toString())
            Log.d("Theme", theme.toString())
        }.start()
        // TODO
        //  PreProcess Functions
        //
        // DEBUG Auto Login
        // Interferes UIThread, so we must  use multi-thread with caution

    }

    override fun initData() {

        //userDAO = AppDatabase.getInstance(this).userDao()
        // DEBUG Thread
        //  Set Network
        Thread {
            ConnectUtils.setNetwork(this, NetworkCapabilities.TRANSPORT_WIFI)
        }.start()

    }

    override fun initViews() {
        // TODO SEPARATE FRAGMENTS


    }

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding
            .inflate(layoutInflater, binding.root, true)
    }


    override fun setupToolbar() {
        /**
         * Set TopAppBar
         */
        val topAppBar = findViewById<Toolbar>(R.id.toolbar)
        topAppBar.title = getString(R.string.login_xyw)
        topAppBar.subtitle = getString(R.string.app_intro)
        if (buildVersion < S)
            topAppBar.setBackgroundColor(getColor(R.color.white))

        var isFavClicked = false
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    // Handle favorite icon press
                    if (!isFavClicked) {
                        topAppBar.menu.findItem(R.id.favorite).icon = getDrawable(R.drawable.ic_baseline_favorite_24)
                        toast("Thanks!")
                    } else {
                        topAppBar.menu.findItem(R.id.favorite).icon = getDrawable(R.drawable.ic_outline_favorite_border_24)
                        toast("Sad!")
                    }
                    isFavClicked = !isFavClicked
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

        /**
         * Set BottomNavigationBar
         */
        val bottomNavigationBar = findViewById<NavigationBarView>(R.id.bottom_navigation)

        var isPage1Selected = true
        var isPage2Selected = false
        var isPage3Selected = false
        bottomNavigationBar.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.page_1 -> {
                        bottomNavigationBar.menu.findItem(R.id.page_1).icon = getDrawable(R.drawable.ic_baseline_star_24)
                        bottomNavigationBar.menu.findItem(R.id.page_2).icon = getDrawable(R.drawable.ic_outline_science_24)
                        bottomNavigationBar.menu.findItem(R.id.page_3).icon = getDrawable(R.drawable.ic_outline_settings_24)
                        true
                    }
                    R.id.page_2 -> {
                        bottomNavigationBar.menu.findItem(R.id.page_1).icon = getDrawable(R.drawable.ic_outline_star_border_24)
                        bottomNavigationBar.menu.findItem(R.id.page_2).icon = getDrawable(R.drawable.ic_baseline_science_24)
                        bottomNavigationBar.menu.findItem(R.id.page_3).icon = getDrawable(R.drawable.ic_outline_settings_24)
                        true
                    }
                    R.id.page_3 -> {
                        bottomNavigationBar.menu.findItem(R.id.page_1).icon = getDrawable(R.drawable.ic_outline_star_border_24)
                        bottomNavigationBar.menu.findItem(R.id.page_2).icon = getDrawable(R.drawable.ic_outline_science_24)
                        bottomNavigationBar.menu.findItem(R.id.page_3).icon = getDrawable(R.drawable.ic_baseline_settings_24)
                        true
                    }
                    else -> false
                }
            }
        }
        // Reselection Handler
/*        bottomNavigationBar.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    // Respond to navigation item 1 reselection
                }
                R.id.page_2 -> {
                    // Respond to navigation item 2 reselection
                }
                R.id.page_3 -> {

                }
            }
        }*/
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


    // TODO FRAGMENT CONTROLLER
    //用来控制Fragment的显示隐藏
    fun showFragment(fragment: Fragment, fm: FragmentManager) {
        //判断当前显示的是否是需要展示的Fragment，可以省略不必要步骤
        if (currentFragment != fragment) {
            //隐藏当前Fragment
            val transaction = fm.beginTransaction()
            //transaction.hide(currentFragment)
            //将fragment替换成目前传入的fragment
            //currentFragment = fragment as
            //判断当前fragment是否添加进事务中
            if (!fragment.isAdded) {
                //添加显示
                transaction.add(R.id.fragment1, fragment).show(fragment).commit()
            } else {
                //显示
                transaction.show(fragment).commit()
            }
        }
    }

}