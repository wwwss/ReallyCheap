package tsingcloud.android.reallycheap.classify.widgets.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.model.bean.ProductBean;
import tsingcloud.android.model.bean.SmallClassifyBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.widgets.activity.ProductActivity;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;
import tsingcloud.android.reallycheap.widgets.view.SAGridView;

/**
 * Created by admin on 2016/3/21.
 * 小分类适配器
 */
public class SmallClassifyAdapter extends BaseAdapter<SmallClassifyBean> {
    private DetailClassifyAdapter adapter;

    public SmallClassifyAdapter(Context context, List<SmallClassifyBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_small_classify, null);
            ItemCache itemCache = new ItemCache();
            itemCache.tvSmallClassifyName = (TextView) convertView.findViewById(R.id.smallClassifyName);
            itemCache.ivEnter = (ImageView) convertView.findViewById(R.id.enter);
            itemCache.saGridView = (SAGridView) convertView.findViewById(R.id.smallClassifyGridView);
            convertView.setTag(itemCache);
        }
        ItemCache itemCache = (ItemCache) convertView.getTag();
        final SmallClassifyBean smallClassifyBean = list.get(position);
        itemCache.tvSmallClassifyName.setText(smallClassifyBean.getName());
        adapter = new DetailClassifyAdapter(context, smallClassifyBean.getList());
        itemCache.saGridView.setAdapter(adapter);
        itemCache.saGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductBean productBean= (ProductBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("productBean", productBean);
                context.startActivity(intent);
            }
        });
        itemCache.ivEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("smallClassifyBean", smallClassifyBean);
                intent.putExtra("type", 0);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private class ItemCache {
        private TextView tvSmallClassifyName;
        private ImageView ivEnter;
        private SAGridView saGridView;
    }


}
