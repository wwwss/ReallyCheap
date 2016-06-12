package tsingcloud.android.core.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import tsingcloud.android.core.callback.ResultCallback;
import tsingcloud.android.core.utils.JsonUtils;
import tsingcloud.android.core.utils.LogUtils;

/**
 * Description : OkHttp网络连接封装工具类
 */
public class OkHttpUtils {

    private final String TAG = getClass().getName();
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
    }

    private synchronized static OkHttpUtils getmInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }

    /**
     * Get请求无参
     *
     * @param url      地址
     * @param callback 回调
     */
    private void getRequest(String url, final ResultCallback callback, String tag) {
        LogUtils.i(TAG, "Get请求的URL是" + url);
        final Request request = new Request.Builder().url(url).tag(tag).build();
        deliveryResult(callback, request);
    }

    /**
     * Get请求有参
     *
     * @param url      地址
     * @param callback 回调
     * @param params   参数
     */
    private void getRequest(String url, final ResultCallback callback, Map<String, String> params, String tag) {
        Request request = buildGetRequest(url, params, tag);
        deliveryResult(callback, request);
    }


    /**
     * Post请求
     *
     * @param url      地址
     * @param callback 回调
     * @param params   参数
     */
    private void postRequest(String url, final ResultCallback callback, Map<String, String> params, String tag) {
        Request request = buildPostRequest(url, params, tag);
        deliveryResult(callback, request);
    }

    /**
     * Delete请求
     *
     * @param url      地址
     * @param callback 回调
     * @param params   参数
     */
    private void deleteRequest(String url, final ResultCallback callback, Map<String, String> params, String tag) {
        Request request = buildDeleteRequest(url, params, tag);
        deliveryResult(callback, request);
    }


    /**
     * Put请求
     *
     * @param url      地址
     * @param callback 回调
     * @param params   参数
     */
    private void putRequest(String url, final ResultCallback callback, Map<String, String> params, String tag) {
        Request request = buildPutRequest(url, params, tag);
        deliveryResult(callback, request);
    }

    private void deliveryResult(final ResultCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String str = response.body().string();
                    if (TextUtils.isEmpty(str)) return;
                    LogUtils.d(TAG, "请求返回的结果是----" + str);
                    JSONObject jsonObject = new JSONObject(str);
                    int errcode = jsonObject.optInt("errcode");
                    if (callback.mType == String.class) {
                        if (errcode == 2)
                            sendTokenFailCallback(callback);
                        else
                            sendSuccessCallBack(callback, str);
                    } else {
                        Object object = JsonUtils.deserialize(str, callback.mType);
                        if (errcode == 2)
                            sendTokenFailCallback(callback);
                        else
                            sendSuccessCallBack(callback, object);
                    }
                } catch (final Exception e) {
                    LogUtils.e(TAG, "convert json failure", e);
                    sendFailCallback(callback, e);
                }

            }
        });
    }


    private void sendTokenFailCallback(final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onTokenFailure();
                }
            }
        });
    }

    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    /**
     * 构建Post请求参数
     *
     * @param url    网络地址
     * @param params 参数
     * @return Request请求参数
     */
    private Request buildPostRequest(String url, Map<String, String> params, String tag) {
        LogUtils.i(TAG, "Post请求的URL是" + url);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (null == entry.getValue())
                continue;
            builder.add(entry.getKey(), entry.getValue());
            LogUtils.i(TAG, "Post请求的参数是" + entry.getKey() + "=" + entry.getValue());
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).tag(tag).build();
    }

    /**
     * 构建Get请求参数
     *
     * @param url    网络地址
     * @param params 参数
     * @return Request请求参数
     */
    private Request buildGetRequest(String url, Map<String, String> params, String tag) {
        String urlParams = null;
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                if (null == entry.getValue())
                    continue;
                buffer.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        urlParams = "?" + buffer;
        String newUrl = url;
        if (!TextUtils.isEmpty(urlParams)) {
            newUrl = url + urlParams;
        }
        LogUtils.i(TAG, "Get请求的URL是" + newUrl);
        return new Request.Builder().url(newUrl).tag(tag).build();
    }

    /**
     * 构建Delete请求参数
     *
     * @param url    网络地址
     * @param params 参数
     * @return Request请求参数
     */
    private Request buildDeleteRequest(String url, Map<String, String> params, String tag) {
        LogUtils.i(TAG, "Delete请求的URL是" + url);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (null == entry.getValue())
                continue;
            builder.add(entry.getKey(), entry.getValue());
            LogUtils.i(TAG, "Delete请求的参数是" + entry.getKey() + "=" + entry.getValue());
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).delete(requestBody).tag(tag).build();
    }

    /**
     * 构建Put请求参数
     *
     * @param url    网络地址
     * @param params 参数
     * @return Request请求参数
     */
    private Request buildPutRequest(String url, Map<String, String> params, String tag) {
        LogUtils.i(TAG, "Put请求的URL是" + url);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (null == entry.getValue())
                continue;
            builder.add(entry.getKey(), entry.getValue());
            LogUtils.i(TAG, "Put请求的参数是" + entry.getKey() + "=" + entry.getValue());
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).tag(tag).put(requestBody).build();
    }


    /**********************
     * 对外接口
     ************************/

    /**
     * get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(String url, ResultCallback callback, String tag) {
        getmInstance().getRequest(url, callback, tag);
    }

    /**
     * get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(String url, ResultCallback callback, Map<String, String> params, String tag) {
        getmInstance().getRequest(url, callback, params, tag);
    }

    /**
     * post请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param params   请求参数
     */
    public static void post(String url, final ResultCallback callback, Map<String, String> params, String tag) {
        getmInstance().postRequest(url, callback, params, tag);
    }

    /**
     * delete请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param params   请求参数
     */
    public static void delete(String url, final ResultCallback callback, Map<String, String> params, String tag) {
        getmInstance().deleteRequest(url, callback, params, tag);
    }

    /**
     * put请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param params   请求参数
     */
    public static void put(String url, final ResultCallback callback, Map<String, String> params, String tag) {
        getmInstance().putRequest(url, callback, params, tag);
    }

    public static void cancelRequest(String tag) {
        LogUtils.i("正在取消网络请求------------------------", "TAG==" + tag);
        getmInstance().mOkHttpClient.cancel(tag);
    }
//
//    public static void sendRequest(RequestBean requestBean, ResultCallback callback) {
//        if (requestBean == null) return;
//        switch (requestBean.getRequestEnum()) {
//            case GET:
//                if (requestBean.getMap() == null)
//                    getmInstance().getRequest(requestBean.getUrl(), callback, requestBean.getTag());
//                else
//                    getmInstance().getRequest(requestBean.getUrl(), callback, requestBean.getMap(), requestBean.getTag());
//                break;
//            case POST:
//            case PUT:
//            case DELETE:
//                getmInstance().Request(requestBean, callback);
//                break;
//        }
//
//    }
//    private void Request(RequestBean requestBean, ResultCallback callback) {
//        FormEncodingBuilder builder = new FormEncodingBuilder();
//        for (Map.Entry<String, String> entry : requestBean.getMap().entrySet()) {
//            if (null == entry.getValue())
//                continue;
//            builder.add(entry.getKey(), entry.getValue());
//            LogUtils.i(TAG, requestBean.getRequestEnum() + "请求的参数是" + entry.getKey() + "=" + entry.getValue());
//        }
//        RequestBody requestBody = builder.build();
//        Request request = null;
//        switch (requestBean.getRequestEnum()) {
//            case PUT:
//                request = new Request.Builder().url(requestBean.getUrl()).put(requestBody).tag(requestBean.getTag()).build();
//                break;
//            case POST:
//                request = new Request.Builder().url(requestBean.getUrl()).post(requestBody).tag(requestBean.getTag()).build();
//                break;
//            case DELETE:
//                request = new Request.Builder().url(requestBean.getUrl()).delete(requestBody).tag(requestBean.getTag()).build();
//                break;
//        }
//        if (request == null) return;
//        deliveryResult(callback, request);
//    }


}
