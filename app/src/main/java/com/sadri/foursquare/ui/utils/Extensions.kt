package com.sadri.foursquare.ui.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sadri.foursquare.ui.utils.mvi.BaseState
import com.sadri.foursquare.ui.utils.mvi.TAG
import com.sadri.foursquare.ui.utils.mvi.model.BaseViewState
import timber.log.Timber

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
        icon.getDrawable(this.context)
    )
}

fun Int.getDrawable(context: Context): Drawable? {
    return when (this) {
        Resources.ID_NULL -> null
        else -> ContextCompat.getDrawable(context, this)
    }
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

fun Context?.snackBar(
    message: Int,
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

fun BaseFragment.renderDefaults(baseViewState: BaseViewState) {
    val base = baseViewState.base

    when (val msg = base.message) {
        is BaseState.Message.SnackBarString -> {
            requireContext().snackBar(
                msg.snackBarMessageString,
                container = container()
            )
        }
        is BaseState.Message.SnackBarRes -> {
            requireContext().snackBar(
                msg.snackBarMessage,
                container = container()
            )
        }
        is BaseState.Message.ToastString -> {
            requireContext().toast(msg.message)
        }
        is BaseState.Message.ToastRes -> {
            requireContext().toast(msg.message)
        }
        is BaseState.Message.Disabled -> {
            Timber.d("$TAG Render Base state Message with disabled value so ignored")
        }
    }

    handleLoadingState(base.showLoading)
}