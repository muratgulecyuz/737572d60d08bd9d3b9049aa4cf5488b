package com.example.unitedspacetraveler.ui.tabs.favorites

import android.os.Bundle
import android.view.View
import com.example.unitedspacetraveler.BR
import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseFragment
import com.example.unitedspacetraveler.databinding.FragmentFavoritesBinding
import com.example.unitedspacetraveler.databinding.ItemFavoriteLayoutBinding
import com.example.unitedspacetraveler.localdata.StationsDatabaseModel
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment(override val layoutId: Int = R.layout.fragment_favorites) : BaseFragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModel()
    private lateinit var favoritesAdapter: LastAdapter

    override fun setUpBinding() {
        binding = bBinding as FragmentFavoritesBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModel.favoritesInfo.observe(viewLifecycleOwner, {
            viewModel.adapterList.clear()
            it?.let {
                viewModel.adapterList.addAll(it)
                favoritesAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun initAdapter() {
        favoritesAdapter = LastAdapter(
            viewModel.adapterList,
            BR.item
        ).map<StationsDatabaseModel>(Type<ItemFavoriteLayoutBinding>(R.layout.item_favorite_layout).onBind { holder ->
            val data = holder.binding.item
            data?.let { favorite ->
                holder.binding.tvName.text = favorite.name
                holder.binding.tvUniversalSpaceTime.text = "EUS"
                holder.binding.ivFavorite.setOnClickListener {
                    viewModel.removeFavorite(favorite.id!!)
                }
            }

        }).into(binding.rvFavorites)
    }
}