package tsingcloud.android.core.interfaces;

/**
 * Created by admin on 2016/3/24.
 * 网络请求监听
 */
public interface OnNSURLRequestListener<T> {
    void onSuccess(T response);
    void onFailure(String msg);
    void onTokenFailure();
}
