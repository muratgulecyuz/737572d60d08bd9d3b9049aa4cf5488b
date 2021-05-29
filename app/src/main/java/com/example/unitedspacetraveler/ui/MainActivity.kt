package com.example.unitedspacetraveler.ui

import android.os.Bundle
import android.view.View
import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseActivity
import com.example.unitedspacetraveler.databinding.ActivityMainBinding
import com.example.unitedspacetraveler.utils.viewBinding

class MainActivity : BaseActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun prepareView(savedInstanceState: Bundle?) {

    }

    override fun getRootView(): View {
        return binding.root
    }
}