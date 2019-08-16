package com.example.aac_library.utils.image;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author: JingYuchun
 * @date: 2019/7/26 15:40
 * @desc: 策略模式封装图片加载框架 方便替换其他框架如 Picasso 效率更高
 */
interface BaseImageLoaderStrategy {
    //统一显示图片
    void displayImage(Context context, ImageView imageView, Object imgUrl, RequestOptions requestOptions);

    void displayCircleImage(Context context, ImageView imageView, Object imgUrl, RequestOptions requestOptions);

    void displayRoundCornerImage(Context context, ImageView imageView, Object imgUrl, RequestOptions requestOptions, int cornerSize);
}
