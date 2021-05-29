package com.example.unitedspacetraveler.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (useDataBinding()) {
            setContentView(getRootView())
        } else {
            setContentView(getLayoutId())
        }
        prepareView(savedInstanceState)
    }

    open fun useDataBinding(): Boolean {
        return true
    }

    abstract fun prepareView(savedInstanceState: Bundle?)

    @LayoutRes
    open fun getLayoutId(): Int {
        return -1
    }

    abstract fun getRootView(): View


}