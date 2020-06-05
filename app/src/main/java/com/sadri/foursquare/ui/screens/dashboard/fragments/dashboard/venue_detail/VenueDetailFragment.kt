package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.sadri.foursquare.R
import com.sadri.foursquare.ui.navigation.NavigationFragment
import com.sadri.foursquare.ui.navigation.NavigationViewModel
import javax.inject.Inject

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
class VenueDetailFragment : NavigationFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: VenueDetailViewModel by viewModels { viewModelFactory }

    override fun getViewModel(): NavigationViewModel = viewModel

    private val args: VenueDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_venue_detail, container, false)
    }
}