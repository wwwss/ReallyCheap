package tsingcloud.android.core.callback;

/**
 * Created by admin on 2016/5/25.
 */

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * http请求回调类,回调方法在UI线程中执行
 *
 * @param <T>
 */
public  abstract class ResultCallback<T> {

    public Type mType;

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 请求成功回调
     *
     * @param response
     */
    public abstract void onSuccess(T response);

    /**
     * 请求失败回调
     *
     * @param e
     */
    public abstract void onFailure(Exception e);


    public  void onTokenFailure(){

    }

}
