package com.example.wanandroid.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * Created by Miracle on 2020/8/1
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class ImageViewAttrAdapter {

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
