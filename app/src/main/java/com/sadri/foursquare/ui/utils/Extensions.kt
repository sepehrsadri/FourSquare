package com.sadri.foursquare.ui.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
fun Context?.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    this?.let {
        Toast.makeText(it, message, duration).show()
    }
}

fun Context?.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_LONG) {
    this?.let {
        Toast.makeText(it, it.getString(resId), duration).show()
    }
}

fun AppCompatImageView.setSrcCompat(
    @DrawableRes icon: Int
) {
    this.setImageDrawable(
        ContextCompat.getDrawable(
            this.context,
            icon
        )
    )
}

fun TextView.setTextFromResources(
    @StringRes res: Int
) {
    this.text = context.resources.getString(res)
}

fun TextView.setTextFromResourcesWithArg(
    @StringRes res: Int,
    argument: String
) {
    val text = context.resources.getString(res, argument)
    this.text = text
}

fun Context?.snackBar(
    message: String,
    container: View
) {
    this?.let {
        Snackbar.make(
            container,
            message,
            Snackbar.LENGTH_SHORT
        ).setAnimationMode(
            Snackbar.ANIMATION_MODE_FADE
        ).show()
    }
}