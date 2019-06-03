package com.example.aacdemo.demo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.aacdemo.R

@BindingAdapter(value = ["app:imgUrl"], requireAll = true)
fun loadImage(view: ImageView, url: String) {

    Glide.with(view.context).load(url).placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(view)
}