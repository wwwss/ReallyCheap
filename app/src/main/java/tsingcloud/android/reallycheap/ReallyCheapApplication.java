package tsingcloud.android.reallycheap;

import tsingcloud.android.core.BaseApplication;

/**
 * Created by admin on 2016/3/16.
 * 程序入口
 */
public class ReallyCheapApplication extends BaseApplication{

    // log标签
    protected final String TAG = getClass().getName();

    public static int count;


    @Override
    public void onCreate() {
        super.onCreate();
        initData();

    }

    private void initData() {
//        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//                count++;
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
//                count--;
//                if (count==0)
//                    LogUtils.d(TAG,"----------------app在后台-------------------");
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//
//            }
//        });

    }



}
