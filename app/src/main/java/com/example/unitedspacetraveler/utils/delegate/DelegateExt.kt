package com.example.unitedspacetraveler.utils

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.unitedspacetraveler.utils.delegate.ActivityViewBindingDelegate
import com.example.unitedspacetraveler.utils.delegate.FragmentViewBindingDelegate

fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T
) = FragmentViewBindingDelegate(this, viewBindingFactory)

fun <T : ViewBinding> AppCompatActivity.viewBinding(
    bindingInflater: (LayoutInflater) -> T
) = ActivityViewBindingDelegate(this, bindingInflater)