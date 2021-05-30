package com.example.unitedspacetraveler.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment() : Fragment() {

    abstract val layoutId: Int
    var bBinding: ViewDataBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return bBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpBinding()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun setUpBinding()

}