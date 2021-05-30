package com.example.unitedspacetraveler.ui.tabs.stations

import android.os.Bundle
import android.view.View
import com.example.unitedspacetraveler.BR
import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseFragment
import com.example.unitedspacetraveler.databinding.FragmentStationsBinding
import com.example.unitedspacetraveler.databinding.ItemStationsLayoutBinding
import com.example.unitedspacetraveler.localdata.SpaceCraftDatabaseModel
import com.example.unitedspacetraveler.localdata.StationsDatabaseModel
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type

import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class StationsFragment(override val layoutId: Int = R.layout.fragment_stations) : BaseFragment() {
    private val viewModel: StationsViewModel by viewModel()
    private lateinit var binding: FragmentStationsBinding

    override fun setUpBinding() {
        binding = bBinding as FragmentStationsBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSpaceCraft()
        observeStations()
        observeDbStations()
    }

    private fun observeDbStations() {
        viewModel.stationsInfo.observe(viewLifecycleOwner, {
            it?.let {
                initAdapter(it)
            }
        })
    }

    private fun observeSpaceCraft() {
        viewModel.spaceCraftInfo.observe(viewLifecycleOwner, {
            it?.let {
                initUi(it)
            }
        })
    }

    private fun observeStations() {
        viewModel.stationsResponse.observe(viewLifecycleOwner, { response ->
            when (response.status) {
                STATUS_LOADING -> {

                }
                STATUS_ERROR -> {

                }
                STATUS_SUCCESS -> {
                    response.responseObject?.let { viewModel.addStations(it) }
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
    }

    private fun initAdapter(stationList: List<StationsDatabaseModel>) {
        if (stationList.isNullOrEmpty().not()) {
            val pagerAdapter = LastAdapter(stationList, BR.item)
                .map<StationsDatabaseModel>(
                    Type<ItemStationsLayoutBinding>(R.layout.item_stations_layout).onBind { holder ->
                        val data = holder.binding.item
                        data?.let { station ->
                            holder.binding.tvCapacityStock.text =
                                "${station.stock} / ${station.capacity}"
                            holder.binding.tvUniversalSpaceTime.text = "EUS"
                            holder.binding.tvPlanetName.text = station.name
                            if (station.isFavorite) {
                                holder.binding.ivFavorite.setImageResource(R.drawable.ic_space)
                            } else {
                                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite)

                            }
                            holder.binding.ivFavorite.setOnClickListener {
                                viewModel.setFavorite(station.isFavorite.not(), station.id!!)
                            }
                        }


                    }

                )
            binding.vpPlanets.adapter = pagerAdapter
        }
    }


}