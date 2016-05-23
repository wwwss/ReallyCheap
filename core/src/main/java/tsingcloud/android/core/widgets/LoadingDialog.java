package tsingcloud.android.core.widgets;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tsingcloud.android.core.R;

/**
 * Created by admin on 2016/4/18.
 */
public class LoadingDialog extends Dialog {

    private Context context;
    private String message;

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView();
    }

    public LoadingDialog(Context context, int themeResId, String message) {
        super(context, themeResId);
        this.context = context;
        this.message = message;
        initView();
    }

    public void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_loading_hint_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);// 加载布局
        ImageView spaceshipImage = (ImageView) view.findViewById(R.id.img);
        TextView tvTip = (TextView) view.findViewById(R.id.tipTextView);// 提示文字
        if (!TextUtils.isEmpty(message)) {
            tvTip.setText(message);
            tvTip.setVisibility(View.VISIBLE);
        }
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.load_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        setCancelable(false);
    }

}
