package tsingcloud.android.reallycheap.classify.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/3/21.
 * 详细分类适配器
 */
public class DetailClassifyAdapter extends BaseAdapter<ProductBean> {

    public DetailClassifyAdapter(Context context, List<ProductBean> list) {
        super(context, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_detail_classify, null);
            ItemCache itemCache = new ItemCache();
            itemCache.ivProductImage = (ImageView) convertView.findViewById(R.id.productImage);
            itemCache.tvProductName = (TextView) convertView.findViewById(R.id.productName);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        ProductBean productBean = list.get(position);
        ImageLoaderUtils.display(context, itemCache.ivProductImage, productBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
        itemCache.tvProductName.setText(productBean.getName());
        return convertView;
    }


    private class ItemCache {
        private ImageView ivProductImage;// 图片
        private TextView tvProductName;
    }


}
