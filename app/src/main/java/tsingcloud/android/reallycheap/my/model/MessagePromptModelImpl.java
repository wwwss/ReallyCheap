package tsingcloud.android.reallycheap.my.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.api.Api;
import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.core.okhttp.OkHttpUtils;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.CollectBabyBean;
import tsingcloud.android.model.bean.MessagePromptBean;

/**
 * Created by admin on 2016/4/29.
 * 收藏宝贝数据实现类
 */
public class MessagePromptModelImpl implements MessagePromptModel {
    @Override
    public void getMessagePromptList(Map<String, String> map, final OnNSURLRequestListener<List<MessagePromptBean>> listener, final OnSetListTotalPagesListener totalPagesListener, String tag) {
        OkHttpUtils.get(Api.MESSAGE_PROMPT, new OkHttpUtils.ResultCallback<ApiResponseBean<List<MessagePromptBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<MessagePromptBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getObjList());
                    totalPagesListener.setTotalPages(response.getTotal_pages());
                } else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("获取消息失败");
            }
        }, map, tag);

    }

    @Override
    public void deleteMessagePrompt(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.delete(Api.MESSAGE_PROMPT, new OkHttpUtils.ResultCallback<ApiResponseBean<List<CollectBabyBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<CollectBabyBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getErrmsg());
                } else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("删除消息失败");
            }
        }, map, tag);
    }

    @Override
    public void clearMessagePrompt(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.get(Api.DELETE_MESSAGE_PROMPT, new OkHttpUtils.ResultCallback<ApiResponseBean<List<CollectBabyBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<CollectBabyBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getErrmsg());
                } else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("清空消息失败");
            }
        }, map, tag);
    }

    @Override
    public void readMessagePrompt(Map<String, String> map, final OnNSURLRequestListener<String> listener, String tag) {
        OkHttpUtils.get(Api.READ_MESSAGE_PROMPT, new OkHttpUtils.ResultCallback<ApiResponseBean<List<CollectBabyBean>>>() {
            @Override
            public void onSuccess(ApiResponseBean<List<CollectBabyBean>> response) {
                if (response.isSuccess()) {
                    listener.onSuccess(response.getErrmsg());
                } else
                    listener.onFailure(response.getErrmsg());
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("阅读消息失败");
            }
        }, map, tag);
    }

}
