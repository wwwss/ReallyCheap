package tsingcloud.android.reallycheap.my.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;

import java.util.List;

import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.widgets.adapter.BaseAdapter;

/**
 * 
 * @author admin
 * 搜索位置适配器
 */
public class SearchLocationAdapter extends BaseAdapter<PoiItem> {
	public SearchLocationAdapter(Context context, List<PoiItem> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_seach_location, null);
			ItemCache itemCache = new ItemCache();
			itemCache.tv_name = (TextView) convertView
					.findViewById(R.id.name);
			itemCache.tv_address = (TextView) convertView
					.findViewById(R.id.address);
			convertView.setTag(itemCache);
		}
		ItemCache itemCache = (ItemCache) convertView.getTag();
		PoiItem poiItem = list.get(position);
		itemCache.tv_name.setText(poiItem.getAdName());
		itemCache.tv_address.setText(poiItem.getCityName()+poiItem.getAdName()+poiItem.getSnippet());
		return convertView;
	}
	private class ItemCache {
		public TextView tv_name;// 地名
		public TextView tv_address;// 详细地址
	}



}
