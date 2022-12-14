package com.furkanbalci.travelguide.util.extensions

import android.widget.ImageView
import androidx.core.graphics.toColorInt
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.furkanbalci.travelguide.R

fun ImageView.download(url: String) {

    //Options
    val options = RequestOptions()
        .placeholder(placeholderProgressBar(this))
        .error(R.color.dark_gray)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()

    //Set image
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

private fun placeholderProgressBar(view: ImageView): CircularProgressDrawable {
    return CircularProgressDrawable(view.context).apply {
        //Change color
        setColorSchemeColors("#FF4760".toColorInt())
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

@BindingAdapter("android:downloadUrl")
fun downloadUrl(view: ImageView, url: String?) {
    url?.let {
        view.download(it)
    }
}
