package tsingcloud.android.reallycheap.homepage.widgets.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.custom.vg.list.CustomListView;
import com.custom.vg.list.OnItemClickListener;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import tsingcloud.android.core.cache.LocalCache;
import tsingcloud.android.core.interfaces.CancelSearchListener;
import tsingcloud.android.core.widgets.activity.BaseActivity;
import tsingcloud.android.model.bean.HotSearchBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.homepage.presenter.SearchPresenter;
import tsingcloud.android.reallycheap.homepage.view.SearchView;
import tsingcloud.android.reallycheap.homepage.widgets.adapter.HistorySearchAdapter;
import tsingcloud.android.reallycheap.homepage.widgets.adapter.HotSearchAdapter;
import tsingcloud.android.reallycheap.utils.ListViewUtils;
import tsingcloud.android.reallycheap.widgets.view.SearchDialog;

/**
 * Created by admin on 2016/3/24.
 * 搜索一级页面
 */
public class SearchActivity extends BaseActivity implements SearchView, View.OnClickListener, CancelSearchListener {

    private static final int SEARCH_RESULTS = 1001;
    private EditText etSearchInput;
    private CustomListView hotSearchListView;
    private HotSearchAdapter hotSearchAdapter;
    private List<HotSearchBean> hotSearchList;
    private SearchPresenter searchPresenter;
    private ListView historySearchListView;
    private HistorySearchAdapter historySearchAdapter;
    private List<String> historySearchList;
    // 语音听写对象
    private SpeechRecognizer speechRecognizer;
    private TextView tvContent;
    private int ret = 0; // 函数调用返回值
    private boolean ISING;
    private SearchDialog searchDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void setUpView() {
        SpeechUtility.createUtility(this, "appid=574918bf");
        speechRecognizer = SpeechRecognizer.createRecognizer(SearchActivity.this, mInitListener);
        searchPresenter = new SearchPresenter(this);
        findViewById(R.id.back).setOnClickListener(this);
        etSearchInput = (EditText) findViewById(R.id.searchInput);
        tvContent = (TextView) findViewById(R.id.content);
        hotSearchListView = (CustomListView) findViewById(R.id.hotSearchListView);
        hotSearchListView.setDividerWidth(30);
        hotSearchListView.setDividerHeight(10);
        hotSearchList = new ArrayList<>();
        HotSearchBean hotSearchBean = new HotSearchBean();
        hotSearchBean.setName("");
        hotSearchList.add(hotSearchBean);
        hotSearchAdapter = new HotSearchAdapter(this, hotSearchList);
        hotSearchListView.setAdapter(hotSearchAdapter);
        historySearchListView = (ListView) findViewById(R.id.historySearchListView);
        historySearchList = new ArrayList<>();
        historySearchAdapter = new HistorySearchAdapter(this, historySearchList);
        historySearchListView.setAdapter(historySearchAdapter);
        findViewById(R.id.clearSearchHistory).setOnClickListener(this);
        findViewById(R.id.microphone).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handler.sendEmptyMessage(1001);
                return false;
            }
        });
        setSearchListener();

    }

    private void setSearchListener() {
        hotSearchListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchContent = hotSearchList.get(position).getName();
                toSearchResultsActivity(searchContent);
            }
        });
        historySearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchContent = (String) parent.getItemAtPosition(position);
                toSearchResultsActivity(searchContent);
            }
        });
        etSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    String searchContent = etSearchInput.getText().toString();
                    if (TextUtils.isEmpty(searchContent)) {
                        return false;
                    }
                    toSearchResultsActivity(searchContent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void setUpData() {
        searchPresenter.getHotSearchData();
    }

    @Override
    public void onResume() {
        super.onResume();
        searchPresenter.getHistorySearchData(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clearSearchHistory:
                clearSearchHistory();
                break;
        }
    }

    @Override
    public void setHotSearchData(List<HotSearchBean> list) {
        if (list != null) {
            hotSearchList.clear();
            hotSearchList.addAll(list);
            hotSearchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setHistorySearchData(List<String> list) {
        if (list.size() > 0) {
            historySearchList.clear();
            historySearchList.addAll(list);
            historySearchAdapter.notifyDataSetChanged();
            findViewById(R.id.clearSearchHistory).setVisibility(View.VISIBLE);
            ListViewUtils.setListViewHeightBasedOnChildren(historySearchListView);
        }
    }

    @Override
    public void clearSearchHistory() {
        //清除缓存
        historySearchList.clear();
        historySearchAdapter.notifyDataSetChanged();
        findViewById(R.id.clearSearchHistory).setVisibility(View.GONE);
        LocalCache.get(this).remove("historySearch");

    }

    @Override
    public void toSearchResultsActivity(String searchContent) {
        Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
        intent.putExtra("searchContent", searchContent);
        startActivityForResult(intent, SEARCH_RESULTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case SEARCH_RESULTS:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showToast("初始化失败，错误码：" + code);
            }
        }
    };


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1001:
                    if (!ISING) {
                        ISING = true;
                        FlowerCollector.onEvent(SearchActivity.this, "醉食汇");
                        setParam();
                        ret = speechRecognizer.startListening(mRecognizerListener);
                        if (ret != ErrorCode.SUCCESS) {
                            showToast("听写失败,错误码：" + ret);
                        } else {
                            // ToastShow("请开始说话");
                            searchDialog = new SearchDialog(SearchActivity.this, SearchActivity.this);
                            searchDialog.setText("向上滑动取消");
                            searchDialog.setImage(R.drawable.microphone_white_icon);
                            searchDialog.show();
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 参数设置
     */
    public void setParam() {
        // 清空参数
        speechRecognizer.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        speechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        // 设置返回结果格式
        speechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语言
        speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        speechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        speechRecognizer.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        speechRecognizer.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        speechRecognizer.setParameter(SpeechConstant.ASR_PTT, "0");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        speechRecognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        speechRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showToast("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showToast(error.getPlainDescription(true));
            tvContent.setText("没有检测到声音");
            ISING = false;
            searchDialog.dismiss();
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            // ToastShow("结束说话");
            ISING = false;
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);
            if (isLast) {
                etSearchInput.setText(tvContent.getText().toString());
//                saveHistorySearch(tvContent.getText().toString());
//                SearchTask.search(SearchActivity.this, tvContent.getText().toString());
                toSearchResultsActivity(tvContent.getText().toString());
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            // ToastShow("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            // if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            // String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            // Log.d(TAG, "session id =" + sid);
            // }
        }
    };

    private void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mIatResults.put(sn, text);
        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setText(resultBuffer.toString());
        searchDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        speechRecognizer.cancel();
        speechRecognizer.destroy();
    }

    public String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
                // 如果需要多候选结果，解析数组其他字段
                // for(int j = 0; j < items.length(); j++)
                // {
                // JSONObject obj = items.getJSONObject(j);
                // ret.append(obj.getString("w"));
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    @Override
    public void cancel() {
        ISING = false;
        tvContent.setText("语音搜索已取消");
        speechRecognizer.stopListening();
    }

}
