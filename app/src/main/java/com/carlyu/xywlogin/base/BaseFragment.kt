package com.carlyu.xywlogin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.inflateBindingWithGeneric

/**
 * Created by Roman on 2021/1/10
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {
    lateinit var binding: T

    // DEBUG Duplicate for test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = this.inflateBindingWithGeneric(layoutInflater)
        // return inflater.inflate(getLayout(), container, false)
        return binding.root
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initViews()


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    // abstract fun getLayout(): Int

    abstract fun initData()

    abstract fun initViews()
}