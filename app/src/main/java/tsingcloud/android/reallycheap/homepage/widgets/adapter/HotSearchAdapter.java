package tsingcloud.android.reallycheap.homepage.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.custom.vg.list.CustomAdapter;

import java.util.List;

import tsingcloud.android.model.bean.HotSearchBean;
import tsingcloud.android.reallycheap.R;

/**
 * Created by admin on 2016/3/24.
 */
public class HotSearchAdapter extends CustomAdapter {

    private List<HotSearchBean> list;
    private Context context;

    public HotSearchAdapter(Context context, List<HotSearchBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemCache itemCache;
        if (convertView == null) {
            itemCache = new ItemCache();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_hot_search, null);
            itemCache.tvSearchContent = (TextView) convertView.findViewById(R.id.searchContent);
            convertView.setTag(itemCache);
        } else {
            itemCache = (ItemCache) convertView.getTag();
        }
        HotSearchBean hotSearchBean = list.get(position);
        itemCache.tvSearchContent.setText(hotSearchBean.getName());
//        itemCache.tvSearchContent.setText(list.get(position));
        return convertView;
    }

    public class ItemCache {
        public TextView tvSearchContent;
    }
}
