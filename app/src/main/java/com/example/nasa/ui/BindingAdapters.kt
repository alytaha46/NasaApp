package com.example.nasa.ui


import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasa.R
import com.example.nasa.domain.Asteroids
import com.example.nasa.domain.ImageOfTheDay
import com.example.nasa.ui.mainFragment.RecyclerAdapter
import com.example.nasa.viewmodels.ImageApiStatus


@BindingAdapter("setApiStatus", "setImageSrc")
fun setImage(img: ImageView, status: ImageApiStatus, list: List<ImageOfTheDay>?) {
    when (status) {
        ImageApiStatus.LOADING -> {
            img.scaleType = ImageView.ScaleType.CENTER_CROP
            img.setImageResource(R.drawable.loading_animation)
        }
        ImageApiStatus.ERROR -> {
            if (!list.isNullOrEmpty()) {
                useGlide(img, list)
            } else {
                img.scaleType = ImageView.ScaleType.CENTER
                img.setImageResource(R.drawable.ic_connection_error)
            }

        }
        ImageApiStatus.DONE -> {
            if (!list.isNullOrEmpty()) {
                useGlide(img, list)
            }
        }
    }
}

fun useGlide(img: ImageView, list: List<ImageOfTheDay>) {
    img.contentDescription = String.format(
        img.context.getString(R.string.nasa_picture_of_day_content_description_format),
        list[0].title
    )
    Glide.with(img)
        .load(list[0].url)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_broken_image)
        .centerCrop()
        .into(img)
}

@BindingAdapter("noConnection")
fun noConnection(progressBar: ProgressBar, list: List<Asteroids>?) {
    progressBar.visibility = if (!list.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("addList")
fun addList(recyclerView: RecyclerView, list: List<Asteroids>?) {
    val adapter = recyclerView.adapter as RecyclerAdapter
    if (list != null)
        adapter.updateList(list)
}

@BindingAdapter("bindAsteroidStatusImage")
fun bindAsteroidStatusImage(img: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        img.setImageResource(R.drawable.ic_status_potentially_hazardous)
        img.contentDescription =
            img.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        img.setImageResource(R.drawable.ic_status_normal)
        img.contentDescription = img.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

// details fragment //////////////////////////////////////////////////////

@BindingAdapter("asteroidStatusImage")
fun asteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun astronomicalUnitText(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun kmUnitText(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun velocityText(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}


