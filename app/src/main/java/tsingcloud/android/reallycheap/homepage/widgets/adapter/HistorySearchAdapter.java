package tsingcloud.android.reallycheap.homepage.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * Created by admin on 2016/3/24.
 * 历史搜索适配器
 */
public class HistorySearchAdapter extends BaseAdapter<String> {

    public HistorySearchAdapter(Context context, List<String> list) {
        super(context, list);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemCache itemCache;
        if (convertView == null) {
            itemCache = new ItemCache();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_history_search, null);
            itemCache.tvSearchContent = (TextView) convertView.findViewById(R.id.searchContent);
            convertView.setTag(itemCache);
        } else {
            itemCache = (ItemCache) convertView.getTag();
        }
        String searchContent = list.get(position);
        itemCache.tvSearchContent.setText(searchContent);
        return convertView;
    }

    public class ItemCache {
        public TextView tvSearchContent;
    }
}
