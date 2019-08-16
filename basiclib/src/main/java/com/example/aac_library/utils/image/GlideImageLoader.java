package com.example.aac_library.utils.image;

import android.content.Context;
import android.widget.ImageView;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author: JingYuchun
 * @date: 2019/7/26 15:43
 * @desc: Glide加载器
 */
public class GlideImageLoader implements BaseImageLoaderStrategy {


    @Override
    public void displayImage(Context context, ImageView imageView, Object imgUrl, RequestOptions requestOptions) {
        Glide.with(context)
                .load(imgUrl)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    @Override
    public void displayCircleImage(Context context, ImageView imageView, Object imgUrl, RequestOptions requestOptions) {
        Glide.with(context)
                .load(imgUrl)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(requestOptions)
                .override(SizeUtils.dp2px(48))
                .circleCrop()
                .into(imageView);
    }

    @Override
    public void displayRoundCornerImage(Context context, ImageView imageView, Object imgUrl, RequestOptions requestOptions, int cornerSize) {
        Glide.with(context)
                .load(imgUrl)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(requestOptions)
                .transform(new RoundedCornersTransformation(context, SizeUtils.dp2px(cornerSize), 0)).into(imageView);
    }
}
