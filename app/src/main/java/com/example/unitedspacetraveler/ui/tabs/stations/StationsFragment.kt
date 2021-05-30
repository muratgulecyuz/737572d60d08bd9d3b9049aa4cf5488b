package com.example.unitedspacetraveler.ui.tabs.stations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var pagerAdapter: LastAdapter

    override fun setUpBinding() {
        binding = bBinding as FragmentStationsBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeSpaceCraft()
        observeDbStations()
    }

    private fun observeDbStations() {
        viewModel.stationsInfo.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.adapterList.clear()
                viewModel.adapterList.addAll(it)
                pagerAdapter.notifyDataSetChanged()
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

    private fun initUi(
        spaceCraftDatabaseModel: SpaceCraftDatabaseModel
    ) {
        binding.tvSpaceWearNumber.text =
            getString(R.string.SWP, spaceCraftDatabaseModel.spaceSuitNumber)
        binding.tvUniversalSpaceTime.text =
            getString(R.string.UST, spaceCraftDatabaseModel.universalSpaceTime)
        binding.tvStrengthTime.text = getString(R.string.ST, spaceCraftDatabaseModel.strengthTime)
    }

    private fun initAdapter() {
            pagerAdapter = LastAdapter(viewModel.adapterList, BR.item)
                .map<StationsDatabaseModel>(
                    Type<ItemStationsLayoutBinding>(R.layout.item_stations_layout).onBind { holder ->
                        val data = holder.binding.item
                        data?.let { station ->
                            holder.binding.tvCapacityStock.text =
                                "${station.stock} / ${station.capacity}"
                            holder.binding.tvUniversalSpaceTime.text = "EUS"
                            holder.binding.tvPlanetName.text = station.name
                            if (station.isFavorite) {
                                holder.binding.ivFavorite.setImageResource(R.drawable.ic_star_selected)
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