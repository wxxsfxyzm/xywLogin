package com.carlyu.xywlogin.settings

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlyu.xywlogin.adapter.SettingsRecyclerAdapter
import com.carlyu.xywlogin.base.BaseFragment
import com.carlyu.xywlogin.databinding.FragmentSettingsBinding
import com.carlyu.xywlogin.utils.getStatusBarHeight

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val dataSet: Array<SettingsItems> = arrayOf(
        SettingsItems("test1", "test1Description", true),
        SettingsItems("test2", "test2Description", false)
    )

    override fun initData() {

    }

    override fun initViews() {
        val context = this@SettingsFragment.requireContext()

        val topPadding = context.let { // Get ActionBar height
            val attrs = intArrayOf(android.R.attr.actionBarSize)
            val typedArray = it.obtainStyledAttributes(attrs)
            val actionBarHeight = typedArray.getDimensionPixelSize(0, 0)
            typedArray.recycle()
            actionBarHeight
        } - getStatusBarHeight(context) // The Padding We Need is ActionBar Height - Status Bar Height
        Log.d("topPadding", topPadding.toString())
        // Initialize RecyclerView
        binding.settingsFragmentRecyclerView.apply {
            setPadding(0, topPadding, 0, 0) // Set Padding
            layoutManager = LinearLayoutManager(context)
            adapter = SettingsRecyclerAdapter(dataSet)
        }

    }


}
