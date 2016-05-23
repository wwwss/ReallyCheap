package tsingcloud.android.reallycheap.homepage.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.model.bean.ClassifyBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/3/21.
 * 首页热门分类适配器
 */
public class HotClassifyAdapter extends BaseAdapter<ClassifyBean> {
    public HotClassifyAdapter(Context context, List<ClassifyBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_hot_classify, null);
            ItemCache itemCache = new ItemCache();
            itemCache.tvClassifyName = (TextView) convertView.findViewById(R.id.classifyName);
            itemCache.tvClassifyImage = (ImageView) convertView.findViewById(R.id.classifyImage);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        ClassifyBean classifyBean = list.get(position);
        itemCache.tvClassifyName.setText(classifyBean.getName());
        ImageLoaderUtils.display(context, itemCache.tvClassifyImage, classifyBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
        return convertView;
    }

    private class ItemCache {
        public TextView tvClassifyName;// 分类名称
        public ImageView tvClassifyImage;//分类图片
    }

}
