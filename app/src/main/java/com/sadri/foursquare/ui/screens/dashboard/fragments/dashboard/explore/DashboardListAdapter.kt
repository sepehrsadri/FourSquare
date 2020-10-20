package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.explore

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.textview.MaterialTextView
import com.sadri.foursquare.R
import com.sadri.foursquare.models.venue.Venue
import com.sadri.foursquare.ui.adapter.BindingViewHolder
import com.sadri.foursquare.ui.adapter.GenericListAdapter
import com.sadri.foursquare.ui.utils.setTextFromResourcesWithArg
import com.sadri.foursquare.utils.extractEnDigits

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class DashboardListAdapter(
    private val dashboardListAdapterContract: DashboardListAdapterContract
) : GenericListAdapter<Venue, BindingViewHolder<Venue>>(VenueDiffCallback()) {

    override fun getLayoutId(position: Int, obj: Venue) =
        R.layout.list_item_venue

    override fun getViewHolder(view: View, viewType: Int): BindingViewHolder<Venue> {
        return DashboardListViewHolder(
            view,
            dashboardListAdapterContract
        )
    }

    private class DashboardListViewHolder(
        private val item: View,
        private val dashboardListAdapterContract: DashboardListAdapterContract
    ) : BindingViewHolder<Venue>(item) {
        private val txtVenueName = item.findViewById<MaterialTextView>(
            R.id.venueNameTv
        )

        private val txtVenueCategory = item.findViewById<MaterialTextView>(
            R.id.venueCategoryTv
        )

        private val txtVenueDistance = item.findViewById<MaterialTextView>(
            R.id.venueDistanceTv
        )

        private lateinit var data: Venue

        private val clickListener = View.OnClickListener {
            dashboardListAdapterContract.onVenueClick(data)
        }

        override fun bind(data: Venue) {
            this.data = data

            txtVenueName.text = data.name.extractEnDigits()
            txtVenueCategory.text = data.categories.firstOrNull()?.name
            txtVenueDistance.setTextFromResourcesWithArg(
                R.string.distance_place_holder,
                data.location.distance.toString()
            )

            item.setOnClickListener(clickListener)
        }
    }

    private class VenueDiffCallback : DiffUtil.ItemCallback<Venue>() {
        override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
            return newItem == oldItem
        }
    }
}