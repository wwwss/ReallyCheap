
package tsingcloud.android.reallycheap.widgets.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tsingcloud.android.model.bean.BannerBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.adapter.MyViewPager;

public class SlideShowView extends FrameLayout implements MyViewPager.OnSingleTouchListener {

    // 自动轮播的时间间隔
    private final static long TIME_INTERVAL = 5L;
    // 自动轮播启用开关
    private final static boolean isAutoPlay = true;
    public boolean isPlay = true;
    // 自定义轮播图的资源
    private List<BannerBean> imageList;
    // 放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    // 放圆点的View的list
    private List<View> dotViewsList;
    private MyViewPager viewPager;
    // 当前轮播页
    private int currentItem = 0;
    // 定时任务
    private ScheduledExecutorService scheduledExecutorService;
    private Context context;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }

    };

    public SlideShowView(Context context) {
        this(context, null);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initData();

    }

    /**
     * 开始轮播图切换
     */
    public void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, TIME_INTERVAL, TimeUnit.SECONDS);
    }

    /**
     * 停止轮播图切换
     */
    public void stopPlay() {
        if (scheduledExecutorService != null) {
            isPlay = false;
            scheduledExecutorService.shutdown();
        }

    }

    /**
     * 初始化相关Data
     */
    private void initData() {
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
        imageList = new ArrayList<BannerBean>();
    }

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context) {
        if (imageList == null && imageList.size() == 0) {
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.view_slideshow, this, true);
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();
        // 热点个数与图片特殊相等
        for (int i = 0; i < imageList.size(); i++) {
            ImageView view = new ImageView(context);
            if (TextUtils.isEmpty(imageList.get(i).getImage()))
                view.setTag(R.id.tag_image_url, "");
            else
                view.setTag(R.id.tag_image_url, imageList.get(i).getImage());
            if (i == 0)// 给一个默认图
                view.setBackgroundResource(R.drawable.banner_icon);
            view.setScaleType(ScaleType.FIT_XY);
            imageViewsList.add(view);
            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }
        viewPager = (MyViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);
        viewPager.setOnSingleTouchListener(this);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        if (isAutoPlay) {
            startPlay();
        }
    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ImageView imageView = imageViewsList.get(position);
            ImageLoaderUtils.display(context, imageView, imageView.getTag(R.id.tag_image_url).toString(), R.drawable.banner_default_icon, R.drawable.banner_default_icon);
            ((ViewPager) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }

    }

    /**
     * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == pos) {
                    ((View) dotViewsList.get(pos)).setBackgroundResource(R.drawable.banner_solid_point_icon);
                } else {
                    ((View) dotViewsList.get(i)).setBackgroundResource(R.drawable.banner_hollow_point_icon);
                }
            }
        }

    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    public void setData(List<BannerBean> imageList) {
        if (imageList != null && imageList.size() > 0) {
            this.imageList.clear();
            this.imageList.addAll(imageList);
            initUI(context);
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onSingleTouch() {

    }

}
