package tsingcloud.android.reallycheap.widgets.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tsingcloud.android.reallycheap.R;
import tsingcloud.android.core.interfaces.OnTabSelectedListener;

public class TabView extends LinearLayout implements OnClickListener {

	private Context context;
	private ImageView iv_img;
	private TextView tv_text;
	private int index;
	private int drawableId;
	private int textId;
	private OnTabSelectedListener listenter;

	public TabView(Context context, int index, int drawableId, int textId,
			OnTabSelectedListener listenter) {
		super(context);
		this.context = context;
		this.index = index;
		this.drawableId = drawableId;
		this.textId = textId;
		this.listenter = listenter;
		initView();
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(context).inflate(R.layout.view_tab, this);
		iv_img = (ImageView) findViewById(R.id.tab_img);
		tv_text = (TextView) findViewById(R.id.tab_text);
		iv_img.setImageResource(drawableId);
		tv_text.setText(textId);
		setOnClickListener(this);
		if (index == 0) {
			iv_img.setSelected(true);
			tv_text.setSelected(true);
		}

	};

	@Override
	public void setSelected(boolean selected) {
		iv_img.setSelected(selected);
		tv_text.setSelected(selected);
	}

	@Override
	public void onClick(View v) {
		if (listenter != null) {
			listenter.onTabSelected(index);

		}

	}

}
