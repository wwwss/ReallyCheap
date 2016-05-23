package tsingcloud.android.reallycheap.classify.widgets.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.reallycheap.R;
import tsingcloud.android.model.bean.ClassifyBean;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/3/21.
 * 分类适配器
 */
public class ClassifyAdapter extends BaseAdapter<ClassifyBean> {
    public ClassifyAdapter(Context context, List<ClassifyBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_classify, null);
            ItemCache itemCache = new ItemCache();
            itemCache.tvName = (TextView) convertView.findViewById(R.id.name);
            itemCache.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        ClassifyBean classifyBean = list.get(position);
        itemCache.tvName.setText(classifyBean.getName());

        if (position == selectItem) {
            itemCache.tvName.setTextColor(Color.parseColor("#ff5a1e"));
            itemCache.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));

        } else {
            itemCache.tvName.setTextColor(Color.parseColor("#666666"));
            itemCache.linearLayout.setBackgroundColor(Color.parseColor("#efefef"));
        }
        return convertView;
    }

    private class ItemCache {
        public TextView tvName;// 名称
        public LinearLayout linearLayout;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
