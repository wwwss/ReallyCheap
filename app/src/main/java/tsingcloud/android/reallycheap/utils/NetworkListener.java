package tsingcloud.android.reallycheap.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import tsingcloud.android.core.utils.LogUtils;
import tsingcloud.android.reallycheap.ReallyCheapApplication;

/**
 * Created by admin on 2016/5/24.
 * 网络监听广播
 */
public class NetworkListener extends BroadcastReceiver {
    public static final String NETWORK_CHANGE = "network_change";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.d("NetworkListener", "--------------------");
        if (ReallyCheapApplication.count == 0)
            return;
        Intent broadcast = new Intent();
        broadcast.addCategory(context.getPackageName());
        broadcast.setAction(NETWORK_CHANGE);
        context.sendBroadcast(broadcast);
    }
}
