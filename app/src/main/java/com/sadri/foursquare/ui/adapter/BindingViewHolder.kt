package com.sadri.foursquare.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
abstract class BindingViewHolder<T>(item: View) : RecyclerView.ViewHolder(item) {
    abstract fun bind(data: T)
}