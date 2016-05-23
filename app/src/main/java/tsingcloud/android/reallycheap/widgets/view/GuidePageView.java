package tsingcloud.android.reallycheap.widgets.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.widgets.activity.WelcomeActivity;

public class GuidePageView extends View {

    private View view;

    public GuidePageView(Context context, Activity activity, int drawableId, int index) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.view_guide_page, null);
        initView(context, activity, drawableId, index);
    }

    private void initView(final Context context, final Activity activity, int drawableId, int index) {
        ImageView iv_guidePage = (ImageView) view.findViewById(R.id.imageView);
        ImageView iv_promptlyOpen = (ImageView) view.findViewById(R.id.promptlyOpen);
        iv_guidePage.setImageResource(drawableId);
        if (index == 2) {
            iv_promptlyOpen.setVisibility(View.VISIBLE);
            iv_promptlyOpen.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    LocalCache.get(context).put("isFirst", "isFirst");
                    activity.finish();


                }
            });
        } else {
            iv_promptlyOpen.setVisibility(View.GONE);
        }
    }

    public View getView() {
        return view;
    }

    public GuidePageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public GuidePageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

}
