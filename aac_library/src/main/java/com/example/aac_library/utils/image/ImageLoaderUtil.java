package com.example.aac_library.utils.image;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
/**
 * @author: JingYuchun
 * @date: 2019/7/26 15:46
 * @desc:
 */
public final class ImageLoaderUtil {
    private BaseImageLoaderStrategy imageLoaderStrategy;

    private ImageLoaderUtil() {

    }

    private static RequestOptions requestOptions;
    private static ImageLoaderUtil instance;

    public static ImageLoaderUtil get() {
        if (instance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (instance == null) {
                    instance = new ImageLoaderUtil();
                    requestOptions = new RequestOptions();
                }
            }
        }
        return instance;
    }

    public ImageLoaderUtil setImageLoaderStrategy(BaseImageLoaderStrategy imageLoaderStrategy) {
        this.imageLoaderStrategy = imageLoaderStrategy;
        return this;
    }

    public BaseImageLoaderStrategy getImageLoaderStrategy() {
        return imageLoaderStrategy;
    }

    public void loadImage(Context context, ImageView imageView, Object imgUrl) {
        if (this.imageLoaderStrategy == null) {
            this.imageLoaderStrategy = new GlideImageLoader();
        }
        this.imageLoaderStrategy.displayImage(context, imageView, imgUrl, requestOptions);
    }

    public void loadCircleImage(Context context, ImageView imageView, Object imgUrl) {
        if (this.imageLoaderStrategy == null) {
            this.imageLoaderStrategy = new GlideImageLoader();
        }
        this.imageLoaderStrategy.displayCircleImage(context, imageView, imgUrl, requestOptions);
    }

    public void loadRoundImage(Context context, ImageView imageView, Object imgUrl) {
        if (this.imageLoaderStrategy == null) {
            this.imageLoaderStrategy = new GlideImageLoader();
        }
        this.imageLoaderStrategy.displayRoundCornerImage(context, imageView, imgUrl, requestOptions, 10);
    }

    public ImageLoaderUtil errorImage(int errorResId) {
        if (requestOptions != null) {
            requestOptions.error(errorResId);
        }
        return this;
    }

    public ImageLoaderUtil placeholderImage(int placeholderResId) {
        if (requestOptions != null) {
            requestOptions.placeholder(placeholderResId);
        }
        return this;
    }
}
