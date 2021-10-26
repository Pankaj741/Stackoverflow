package com.example.stackoverflow.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*
import android.content.ActivityNotFoundException
import android.content.Context

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


fun Int.dpToPx() = UtilFunctions.convertDpToPixel(toFloat()).toInt()
fun Int.dpToPxFloat() = UtilFunctions.convertDpToPixel(toFloat())

fun Float.dpToPx() = UtilFunctions.convertDpToPixel(this)

fun Int.spToPx(): Int = toFloat().spToPx().toInt()

fun Float.spToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )
}

@SuppressLint("CheckResult")
fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes errorDrawable: Int? = null,
) {
    if (!UtilFunctions.isValidContextForGlide(this.context)) return
    Glide.with(this)
        .load(url)
        .also { glide ->
            val requestOptions = RequestOptions()
            placeholder?.also { drawable ->
                requestOptions.placeholder(drawable)
            }
            errorDrawable?.let { errorDrawable ->
                requestOptions.error(errorDrawable)
            }
            glide.apply(requestOptions)
        }
        .into(this)
}

@SuppressLint("SimpleDateFormat")
fun Long.setTheDate(): String {
    val curDateTime = Date(this)
//    val formatter = SimpleDateFormat("dd'/'MM'/'y, hh:mm a")
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    return formatter.format(curDateTime)
}

fun Context.openUrlInChrome(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.setPackage("com.android.chrome")
    try {
        this.startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        // Chrome browser presumably not installed so allow user to choose instead
        intent.setPackage(null)
        this.startActivity(intent)
    }
}

infix fun Int.not(value: Int): Boolean = this != value

fun View.hide() {
    if (this.visibility not View.GONE)
        this.visibility = View.GONE
}

fun View.show() {
    if (this.visibility not View.VISIBLE)
        this.visibility = View.VISIBLE
}

val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()
