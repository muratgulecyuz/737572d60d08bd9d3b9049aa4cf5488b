package com.example.unitedspacetraveler.ui.tabs.stations

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.unitedspacetraveler.BR
import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseFragment
import com.example.unitedspacetraveler.databinding.FragmentStationsBinding
import com.example.unitedspacetraveler.databinding.ItemStationsLayoutBinding
import com.example.unitedspacetraveler.localdata.SpaceCraftDatabaseModel
import com.example.unitedspacetraveler.localdata.StationsDatabaseModel
import com.example.unitedspacetraveler.utils.delegate.showToast
import com.example.unitedspacetraveler.utils.setGone
import com.example.unitedspacetraveler.utils.setVisible
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import org.koin.androidx.viewmodel.ext.android.viewModel

class StationsFragment(override val layoutId: Int = R.layout.fragment_stations) : BaseFragment() {
    private val viewModel: StationsViewModel by viewModel()
    private lateinit var binding: FragmentStationsBinding
    private lateinit var pagerAdapter: LastAdapter
    private lateinit var timer: CountDownTimer
    private var isFirstObserve = true

    override fun setUpBinding() {
        binding = bBinding as FragmentStationsBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeSpaceCraft()
    }

    private fun observeDbStations() {
        viewModel.stationsInfo.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isNotEmpty()) {
                    viewModel.adapterList.clear()
                    viewModel.adapterList.addAll(it)
                    pagerAdapter.notifyDataSetChanged()
                    binding.currentPlanetName.text = viewModel.getCurrentPlanetName()
                }
            }
        })
    }

    private fun observeSpaceCraft() {
        viewModel.spaceCraftInfo.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.mySpaceCraft = it
                initUi(it)
                observeDbStations()
                if (isFirstObserve) {
                    initCountDownTimer()
                    isFirstObserve = false
                }
            }
        })
    }

    private fun initUi(
        spaceCraftDatabaseModel: SpaceCraftDatabaseModel
    ) {
        binding.tvSpaceWearNumber.text =
            getString(R.string.SWP, spaceCraftDatabaseModel.spaceSuitNumber)
        binding.tvUniversalSpaceTime.text =
            getString(R.string.UST, spaceCraftDatabaseModel.universalSpaceTime)
        binding.tvStrengthTime.text = getString(R.string.ST, spaceCraftDatabaseModel.strengthTime)
        binding.tvDamage.text = spaceCraftDatabaseModel.spaceCraftDamage.toString()
    }

    private fun initAdapter() {
        pagerAdapter = LastAdapter(viewModel.adapterList, BR.item)
            .map<StationsDatabaseModel>(
                Type<ItemStationsLayoutBinding>(R.layout.item_stations_layout).onBind { holder ->
                    val data = holder.binding.item
                    data?.let { station ->
                        holder.binding.tvCapacityStock.text =
                            "${station.stock} / ${station.capacity}"
                        holder.binding.tvUniversalSpaceTime.text =
                            "${station.universalSpaceTime} EUS"
                        holder.binding.tvPlanetName.text = station.name

                        if (station.isFavorite) {
                            holder.binding.ivFavorite.setImageResource(R.drawable.ic_star_selected)
                        } else {
                            holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                        }

                        if (station.isMissionCompleted) {
                            holder.binding.btnTravel.setGone()
                        } else {
                            holder.binding.btnTravel.setVisible()
                        }
                        holder.binding.ivFavorite.setOnClickListener {
                            viewModel.setFavorite(station)
                        }

                        holder.binding.btnTravel.setOnClickListener {
                            viewModel.travel(station, showToast = { message ->
                                requireContext().showToast(message)
                            }, setPlanetName = {
                                binding.currentPlanetName.text =
                                    "Şuan ${station.name} gezegenindesiniz."
                            })
                        }
                    }


                }

            )
        binding.vpPlanets.adapter = pagerAdapter

    }

    private fun initCountDownTimer() {
        timer = object : CountDownTimer((getDamageTimeInterval() * 1000).toLong(), 1000) {
            override fun onTick(p0: Long) {
                binding.tvTime.text = "${p0 / 1000}s"
            }

            override fun onFinish() {
                viewModel.decreaseDamage()
                if (viewModel.mySpaceCraft.spaceCraftDamage > 0) {
                    timer.start()
                }
            }

        }.start()

    }

    private fun getDamageTimeInterval(): Int {
        return viewModel.mySpaceCraft.strengthTime / 2000
    }


}