package com.sadri.foursquare.ui.utils

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sepehr Sadri on 6/3/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView.canScrollVertically(1).not()) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}