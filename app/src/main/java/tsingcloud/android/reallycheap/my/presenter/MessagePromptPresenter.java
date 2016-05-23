package tsingcloud.android.reallycheap.my.presenter;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.MessagePromptBean;
import tsingcloud.android.reallycheap.my.model.MessagePromptModel;
import tsingcloud.android.reallycheap.my.model.MessagePromptModelImpl;
import tsingcloud.android.reallycheap.my.view.MessagePromptView;

/**
 * Created by admin on 2016/4/29.
 * 收藏宝贝控制器
 */
public class MessagePromptPresenter {
    private MessagePromptView messagePromptView;
    private MessagePromptModel messagePromptModel;

    public MessagePromptPresenter(MessagePromptView messagePromptView) {
        this.messagePromptView = messagePromptView;
        messagePromptModel = new MessagePromptModelImpl();
    }


    public void getMessagePromptList(int pageNum) {
        if (TextUtils.isEmpty(messagePromptView.getToken()) || TextUtils.isEmpty(messagePromptView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", messagePromptView.getToken());
        map.put("shop_id", messagePromptView.getShopId());
        map.put("page_num", pageNum + "");
        messagePromptModel.getMessagePromptList(map, new OnNSURLRequestListener<List<MessagePromptBean>>() {
            @Override
            public void onSuccess(List<MessagePromptBean> response) {
                messagePromptView.setMessagePromptList(response);
            }

            @Override
            public void onFailure(String msg) {
                messagePromptView.showToast(msg);
            }
        }, new OnSetListTotalPagesListener() {
            @Override
            public void setTotalPages(int totalPages) {
                messagePromptView.setTotalPages(totalPages);
            }
        }, messagePromptView.getTAG());
    }

    public void deleteMessagePrompt(String id, final int position) {
        if (TextUtils.isEmpty(messagePromptView.getToken()) || TextUtils.isEmpty(messagePromptView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", messagePromptView.getToken());
        map.put("id", id);
        messagePromptModel.deleteMessagePrompt(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                messagePromptView.showToast(response);
                messagePromptView.deleteComplete(position);
            }

            @Override
            public void onFailure(String msg) {
                messagePromptView.showToast(msg);
            }
        }, messagePromptView.getTAG());
    }

    public void clearMessagePrompt() {
        if (TextUtils.isEmpty(messagePromptView.getToken()) || TextUtils.isEmpty(messagePromptView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", messagePromptView.getToken());
        map.put("shop_id", messagePromptView.getShopId());
        messagePromptModel.clearMessagePrompt(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                messagePromptView.showToast(response);
                messagePromptView.clearComplete();
            }

            @Override
            public void onFailure(String msg) {
                messagePromptView.showToast(msg);
            }
        }, messagePromptView.getTAG());
    }

    public void  readMessagePrompt(String id){
        if (TextUtils.isEmpty(messagePromptView.getToken()) || TextUtils.isEmpty(messagePromptView.getShopId()))
            return;
        Map<String, String> map = new HashMap<>();
        map.put("token", messagePromptView.getToken());
        map.put("id", id);
        messagePromptModel.readMessagePrompt(map, new OnNSURLRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                messagePromptView.showToast(response);
            }

            @Override
            public void onFailure(String msg) {
                messagePromptView.showToast(msg);
            }
        }, messagePromptView.getTAG());

    }


}
