package tsingcloud.android.core;

import android.app.Application;

import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.update.UmengUpdateAgent;

/**
 * Created by admin on 2016/5/10.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 程序启动时像友盟发送统计数据
        OnlineConfigAgent.getInstance().updateOnlineConfig(this);
        // 程序启动时检查是否有更新
        UmengUpdateAgent.setUpdateAutoPopup(true);
        // 任意网络下都提示更新
        UmengUpdateAgent.setUpdateOnlyWifi(true);
        // 关闭自动检查提示
        UmengUpdateAgent.setUpdateCheckConfig(false);
    }

}
