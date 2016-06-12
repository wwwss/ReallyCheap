package tsingcloud.android.reallycheap.model;

import tsingcloud.android.core.callback.ResultCallback;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;

/**
 * Created by admin on 2016/5/25.
 */
public class BaseModelImpl {


    public abstract class AbstractResultCallback<T> extends ResultCallback<T> {
        public OnNSURLRequestListener listener;

        public AbstractResultCallback(OnNSURLRequestListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSuccess(T response) {

        }

        @Override
        public void onTokenFailure() {
            listener.onTokenFailure();
        }

        @Override
        public void onFailure(Exception e) {
            //listener.onFailure("服务器异常，请稍后再试");
        }
    }
}
