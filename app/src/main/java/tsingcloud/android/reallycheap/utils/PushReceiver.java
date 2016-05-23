package tsingcloud.android.reallycheap.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.PushConsts;

import tsingcloud.android.core.utils.LogUtils;

/**
 * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
 */
public class PushReceiver extends BroadcastReceiver {

    /**
     * TAG to Log
     */
    public static final String TAG = PushReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    LogUtils.d(TAG,data);
                }
                break;
            case PushConsts.GET_CLIENTID:
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                //String clientId = bundle.getString("clientid");
                break;
            case PushConsts.THIRDPART_FEEDBACK:
                break;

            default:
                break;
        }
    }
}
