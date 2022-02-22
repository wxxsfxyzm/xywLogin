package com.carlyu.xywlogin.login

import android.content.Context
import android.content.Intent
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.SystemClock.sleep
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.carlyu.xywlogin.R
import com.carlyu.xywlogin.base.BaseActivity
import com.carlyu.xywlogin.common.Constant.S
import com.carlyu.xywlogin.common.Constant.buildVersion
import com.carlyu.xywlogin.databinding.ActivityLoginBinding
import com.carlyu.xywlogin.exception.MyException
import com.carlyu.xywlogin.utils.ConnectUtils
import com.carlyu.xywlogin.utils.toast
import com.google.android.material.navigation.NavigationBarView


class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val loginFragment = LoginFragment() as Fragment
    private val settingsFragment = SettingsFragment() as Fragment

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
        addFragment()
        try {
            switchFragment(currentFragment)
        } catch (e: MyException) {
            e.stackTrace
        }

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

        bottomNavigationBar.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.page_1 -> {
                        navigationToMainFragment(this, topAppBar)
                        true
                    }
                    R.id.page_2 -> {
                        bottomNavigationBar.menu.findItem(R.id.page_1).icon = getDrawable(R.drawable.ic_outline_star_border_24)
                        bottomNavigationBar.menu.findItem(R.id.page_2).icon = getDrawable(R.drawable.ic_baseline_science_24)
                        bottomNavigationBar.menu.findItem(R.id.page_3).icon = getDrawable(R.drawable.ic_outline_settings_24)
                        toast("别搞，还没写好")
                        true
                    }
                    R.id.page_3 -> {
                        navigationToSettingFragment(this, topAppBar)
                        true
                    }
                    else -> false
                }
            }
        }
        // Reselection Handler
        /* bottomNavigationBar.setOnItemReselectedListener { item ->
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

    /**
     * Call finish() after ms
     *
     * @param ms MilliSeconds
     */
    private fun finishAfterMS(ms: Long) {
        sleep(ms)
        super.finish()
    }

    /**
     * Function called when navigate to Main Fragment
     *
     * @param bottomNavigationBar NavigationBarView Instance
     * @param topAppBar Toolbar Instance
     */
    private fun navigationToMainFragment(bottomNavigationBar: NavigationBarView, topAppBar: Toolbar) {
        topAppBar.apply {
            title = getString(R.string.login_xyw)
            subtitle = getString(R.string.app_intro)
        }
        bottomNavigationBar.menu.findItem(R.id.page_1).icon = getDrawable(R.drawable.ic_baseline_star_24)
        bottomNavigationBar.menu.findItem(R.id.page_2).icon = getDrawable(R.drawable.ic_outline_science_24)
        bottomNavigationBar.menu.findItem(R.id.page_3).icon = getDrawable(R.drawable.ic_outline_settings_24)
        if (currentFragment != loginFragment)
            switchFragment(loginFragment)
    }

    /**
     * Function called when navigate to Settings Fragment
     *
     * @param bottomNavigationBar NavigationBarView Instance
     * @param topAppBar Toolbar Instance
     */
    private fun navigationToSettingFragment(bottomNavigationBar: NavigationBarView, topAppBar: Toolbar) {
        topAppBar.apply {
            title = getString(R.string.settings)
            subtitle = getString(R.string.settings_subtitle)
        }
        bottomNavigationBar.menu.findItem(R.id.page_1).icon = getDrawable(R.drawable.ic_outline_star_border_24)
        bottomNavigationBar.menu.findItem(R.id.page_2).icon = getDrawable(R.drawable.ic_outline_science_24)
        bottomNavigationBar.menu.findItem(R.id.page_3).icon = getDrawable(R.drawable.ic_baseline_settings_24)
        if (currentFragment != settingsFragment)
            switchFragment(settingsFragment)

    }


    // TODO FRAGMENT CONTROLLER
    /**
     * 用来控制Fragment的显示隐藏
     * 持久化Fragment
     */
    private fun addFragment() {
        val transaction: FragmentTransaction = supportFragmentManager
            .beginTransaction()
        transaction.apply {
            add(R.id.fragment, loginFragment)
            // TODO Developing Fragment
            // add(R.id.fragment,DevelopingFragment)
            add(R.id.fragment, settingsFragment)
            hide(settingsFragment)
        }.commit()
        //transaction.add(R.id.fragment, settingsFragment).commit()
    }

    /**
     * Fragment Switcher
     * @exception MyException(-256, "targetFragmentNotAdded")
     * @param targetFragment Fragment
     */
    private fun switchFragment(targetFragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager
            .beginTransaction()
        if (!targetFragment.isAdded) {
            // TODO Exception Code
            throw MyException(-256, "targetFragmentNotAdded")
        } else {
            transaction
                .hide(currentFragment)
                .show(targetFragment)
                .commit()
        }
        currentFragment = targetFragment
    }

}