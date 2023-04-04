package com.carlyu.xywlogin.settings

import androidx.recyclerview.widget.LinearLayoutManager
import com.carlyu.xywlogin.adapter.SettingsRecyclerAdapter
import com.carlyu.xywlogin.base.BaseFragment
import com.carlyu.xywlogin.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    // private val

    private val dataSet: Array<SettingsItems> = arrayOf(
        SettingsItems("test1", "test1Description", true),
        SettingsItems("test2", "test2Description", false)
    )

    override fun initData() {
        
    }

    override fun initViews() {
        // Easily view fragment area
        // view?.setBackgroundColor(Color.DKGRAY)

        // Initialize RecyclerView
        binding.settingsFragmentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SettingsRecyclerAdapter(dataSet)
        }

    }


}
