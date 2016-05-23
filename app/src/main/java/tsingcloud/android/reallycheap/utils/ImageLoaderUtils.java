package tsingcloud.android.reallycheap.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Description : 图片加载工具类
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/21
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (TextUtils.isEmpty(url))
            imageView.setImageResource(error);
        if (imageView != null && !TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).placeholder(placeholder)
                    .error(error).crossFade().into(imageView);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param imageView
     * @param url
     * @param placeholder
     * @param error
     */
    public static void displayRoundedImageView(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView != null && !TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).bitmapTransform(new CropCircleTransformation(context)).placeholder(placeholder)
                    .error(error).crossFade().into(imageView);
        }
    }


}
