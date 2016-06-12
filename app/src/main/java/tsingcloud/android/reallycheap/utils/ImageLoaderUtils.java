package tsingcloud.android.reallycheap.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 图片加载工具类
 */
public class ImageLoaderUtils {

    /**
     * 显示图片
     *
     * @param context     上下文
     * @param imageView   图片控件
     * @param url         图片地址
     * @param placeholder 加载中图片
     * @param error       加载错误图片
     */
    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (TextUtils.isEmpty(url))
            imageView.setImageResource(error);
        if (imageView != null && !TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).placeholder(placeholder)
                    .error(error).crossFade().into(imageView);
        }
    }

    /**
     * 显示圆形图片
     *
     * @param context     上下文
     * @param imageView   图片控件
     * @param url         图片地址
     * @param placeholder 加载中图片
     * @param error       加载错误图片
     */
    public static void displayRoundedImageView(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView != null && !TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).bitmapTransform(new CropCircleTransformation(context)).placeholder(placeholder)
                    .error(error).crossFade().into(imageView);
        }
    }


}
