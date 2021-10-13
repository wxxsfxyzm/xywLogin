package com.carlyu.xywlogin.base


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.inflateBindingWithGeneric


/**
 * @author Roman
 * @des
 * @version $
 * @updateAuthor $
 * @updateDes
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = this.inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        initData()
        initViews()
        setupToolbar()
    }

    abstract fun getViewBinding(): T?

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化view
     */
    protected abstract fun initViews()

    /**
     * 设置toolbar
     */
    abstract fun setupToolbar()
}