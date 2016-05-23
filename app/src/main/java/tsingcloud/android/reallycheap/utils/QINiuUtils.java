package tsingcloud.android.reallycheap.utils;

import android.text.TextUtils;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.UploadPicturesListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.core.utils.LogUtils;

/**
 * Created by admin on 2016/4/16.
 * 七牛工具类
 */
public class QINiuUtils {

    private static QINiuUtils mInstance;
    private UploadManager uploadManager;

    private QINiuUtils() {
        uploadManager = new UploadManager();
    }

    public synchronized static QINiuUtils getmInstance() {
        if (mInstance == null) {
            mInstance = new QINiuUtils();
        }
        return mInstance;
    }

    /**
     * 上传文件
     */
    public void UploadFile(final String filePath, final UploadPicturesListener listener,String tag) {
        if (!ToolsUtil.fileIsExists(filePath))
            return;
        listener.onUploadStart();
        OkHttpUtils.get(Api.UPDATE_IMAGE_TOKEN, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errcode = jsonObject.optInt("errcode", -1);
                    if (errcode == 0) {
                        JSONObject jsonObject1 = jsonObject.optJSONObject("obj");
                        if (jsonObject1 == null) {
                            listener.onUploadFailure();
                            return;
                        }
                        String token = jsonObject1.optString("token");
                        if (TextUtils.isEmpty(token)) {
                            listener.onUploadFailure();
                            return;
                        }
                        uploadManager.put(filePath, null, token,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject response) {
                                        LogUtils.d("上传图片完成", "key----" + key + "info----" + info + "response----" + response.toString());
                                        listener.onUploadSuccess(response.optString("key"));
                                    }
                                }, null);
                    } else {
                        listener.onUploadFailure();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onUploadFailure();
                }

            }

            @Override
            public void onFailure(Exception e) {
                listener.onUploadFailure();
            }
        },tag);

    }


}
