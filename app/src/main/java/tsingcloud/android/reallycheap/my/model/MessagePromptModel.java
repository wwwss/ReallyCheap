package tsingcloud.android.reallycheap.my.model;

import java.util.List;
import java.util.Map;

import tsingcloud.android.core.interfaces.OnNSURLRequestListener;
import tsingcloud.android.core.interfaces.OnSetListTotalPagesListener;
import tsingcloud.android.model.bean.MessagePromptBean;

/**
 * Created by admin on 2016/4/29.
 * 收藏宝贝数据接口类
 */
public interface MessagePromptModel {
    /**
     * 获取消息列表
     * @param map                 请求参数集合
     * @param listener            请求返回监听
     * @param totalPagesListener  请求返回列表长度监听
     */
    void getMessagePromptList(Map<String, String> map, OnNSURLRequestListener<List<MessagePromptBean>> listener, OnSetListTotalPagesListener totalPagesListener, String tag);

    /**
     * 删除消息
     * @param map
     * @param listener
     */
    void deleteMessagePrompt(Map<String, String> map, OnNSURLRequestListener<String> listener, String tag);

    /**
     * 清空消息
     * @param map
     * @param listener
     */
    void clearMessagePrompt(Map<String, String> map, OnNSURLRequestListener<String> listener, String tag);

    /**
     * 阅读消息
     * @param map
     * @param listener
     * @param tag
     */
    void readMessagePrompt(Map<String, String> map, OnNSURLRequestListener<String> listener, String tag);
}
