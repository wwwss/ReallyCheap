package tsingcloud.android.reallycheap.widgets.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tsingcloud.android.core.interfaces.CancelSearchListener;
import tsingcloud.android.reallycheap.R;


public class SearchDialog extends Dialog {

	private Context context;
	private TextView tvText;
	private ImageView ivImage;
	private LinearLayout layout;
	private GestureDetector mGestureDetector;
	private CancelSearchListener listener;

	public SearchDialog(Context context, CancelSearchListener listener) {
		super(context, R.style.search_dialog);
		this.context = context;
		this.listener = listener;
		// new一个自定义的手势监听
		this.mGestureDetector = new GestureDetector(context, new ViewGestureListener());
		initView();
	}

	private void initView() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_search, null);
		layout = (LinearLayout) view.findViewById(R.id.layout);
		layout.getBackground().setAlpha(180);
		tvText = (TextView) view.findViewById(R.id.text);
		ivImage = (ImageView) view.findViewById(R.id.image);
		// 定义Dialog布局和参数
		setContentView(view);
		// 调整dialog背景大小
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(params);
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if ("松开手指取消发送".equals(tvText.getText().toString())) {
				listener.cancel();
				dismiss();
			}
			break;
		}
		return mGestureDetector.onTouchEvent(event);
	}

	public void setText(String text) {
		tvText.setText(text);
	}

	public void setImage(int drawable) {
		ivImage.setImageResource(drawable);
	}

	private class ViewGestureListener implements OnGestureListener {
		// 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
		public boolean onDown(MotionEvent e) {
			return false;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return true;
		}

		// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
		public void onLongPress(MotionEvent e) {
		}

		// 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (e1.getRawY() - e2.getRawY() > 20) {
				setImage(R.drawable.loosen_icon);
				setText("松开手指取消发送");
			} else if (e2.getRawY() - e1.getRawY() > 20) {
				setImage(R.drawable.microphone_white_icon);
				setText("向上滑动取消");
			}
			return false;
		}

		// 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
		// 注意和onDown()的区别，强调的是没有松开或者拖动的状态
		public void onShowPress(MotionEvent e) {

		}

		// 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
	}

}
