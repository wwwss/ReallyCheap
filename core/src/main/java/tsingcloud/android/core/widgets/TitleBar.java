package tsingcloud.android.core.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tsingcloud.android.core.R;
import tsingcloud.android.core.interfaces.TitleBarListener;

/**
 * Created by admin on 2016/4/14.
 * 顶部状态栏
 */
public class TitleBar extends LinearLayout {


    private Context context;
    private ImageView ivLeft;
    private TextView tvTitle;
    private ImageView ivRight;
    private TextView tvRight;
    private TitleBarListener listener;


    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setListener(TitleBarListener listener) {
        this.listener = listener;
    }

    private void initView() {
        LayoutInflater.from(context)
                .inflate(R.layout.widget_title_bar, this);
        ivLeft = (ImageView) findViewById(R.id.left);
        tvTitle = (TextView) findViewById(R.id.title);
        tvRight = (TextView) findViewById(R.id.tvRight);
        ivRight = (ImageView) findViewById(R.id.ivRight);
    }

    public void setTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(title))
            tvTitle.setText(title);
    }

    public void setTitle(int titleId) {
        tvTitle.setVisibility(View.VISIBLE);
        if (titleId > 0)
            tvTitle.setText(titleId);
    }

    public void setLeftDrawable(int leftDrawbleId) {
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(leftDrawbleId);
        ivLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickLeft();
            }
        });


    }

    public void setRightDrawable(int rightDrawbleId) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(rightDrawbleId);
        ivRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickRight();
            }
        });
    }

    public void setRightText(int titleId) {
        tvRight.setVisibility(View.VISIBLE);
        if (titleId > 0)
            tvRight.setText(titleId);
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickRight();
            }
        });
    }


}
