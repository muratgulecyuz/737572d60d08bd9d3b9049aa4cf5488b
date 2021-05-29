package com.example.unitedspacetraveler.ui.createspacecraft

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.utilcode.util.ActivityUtils
import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseActivity
import com.example.unitedspacetraveler.databinding.ActivityCreateSpaceCraftBinding
import com.example.unitedspacetraveler.ui.MainActivity
import com.example.unitedspacetraveler.utils.delegate.showToast
import com.example.unitedspacetraveler.utils.viewBinding

class CreateSpaceCraftActivity : BaseActivity() {

    private val binding by viewBinding(ActivityCreateSpaceCraftBinding::inflate)
    private val totalPoint = 15
    private var strengthPoint = 0
    private var speedPoint = 0
    private var capacityPoint = 0
    private val intervalPoint: Double = (100).toDouble() / 15

    override fun prepareView(savedInstanceState: Bundle?) {
        setDistributedPointText()
        strengthSeekbarListener()
        speedSeekbarListener()
        capacitySeekbarListener()
        continueButtonClickListener()
    }

    override fun getRootView(): View {
        return binding.root
    }

    private fun strengthSeekbarListener() {
        binding.sbStrength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                handleProgressChange(progress, strengthPoint, binding.sbStrength) {
                    strengthPoint = (progress / intervalPoint).toInt()
                    setDistributedPointText()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun speedSeekbarListener() {
        binding.sbSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                handleProgressChange(progress, speedPoint, binding.sbSpeed) {
                    speedPoint = (progress / intervalPoint).toInt()
                    setDistributedPointText()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun capacitySeekbarListener() {
        binding.sbCapacity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                handleProgressChange(progress, capacityPoint, binding.sbCapacity) {
                    capacityPoint = (progress / intervalPoint).toInt()
                    setDistributedPointText()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun continueButtonClickListener() {
        binding.btnContinue.setOnClickListener {
            if (isValid()) {
                ActivityUtils.startActivity(MainActivity::class.java)
            }
        }
    }

    private fun getRemainPoint(): Int {
        return totalPoint - (strengthPoint + speedPoint + capacityPoint)
    }

    private fun setDistributedPointText() {
        binding.tvDistributedPoint.text =
            getString(R.string.points_to_ne_distributed, getRemainPoint())
    }

    private fun handleProgressChange(
        progress: Int,
        point: Int,
        seekBar: SeekBar,
        changeFunction: () -> Unit
    ) {
        if (getRemainPoint() < 1 && progress > ((point * intervalPoint)).toInt()) {
            seekBar.progress = ((point * intervalPoint)).toInt()
        } else {
            changeFunction.invoke()
        }
    }

    private fun isValid(): Boolean {
        if (binding.etSpaceCraftName.text.toString().isNullOrEmpty()) {
            showToast(getString(R.string.enter_spacecraft_name))
            return false
        }
        if (getRemainPoint() > 0) {
            showToast(getString(R.string.distribute_points))
            return false
        }

        return true
    }
}