package tsingcloud.android.reallycheap.my.widgets.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tsingcloud.android.core.widgets.activity.BaseListActivity;
import tsingcloud.android.core.widgets.pull.BaseViewHolder;
import tsingcloud.android.core.widgets.pull.PullRecycler;
import tsingcloud.android.model.bean.MessagePromptBean;
import tsingcloud.android.reallycheap.R;
import tsingcloud.android.reallycheap.classify.widgets.activity.ProductDetailsActivity;
import tsingcloud.android.reallycheap.my.presenter.MessagePromptPresenter;
import tsingcloud.android.reallycheap.my.view.MessagePromptView;
import tsingcloud.android.reallycheap.utils.ImageLoaderUtils;
import tsingcloud.android.reallycheap.widgets.view.CustomAlertDialog;

/**
 * Created by admin on 2016/5/3.
 * 消息提示页面
 */
public class MessagePromptActivity extends BaseListActivity<MessagePromptBean> implements MessagePromptView {

    private int pageNum = 1;
    private int totalPages;
    private MessagePromptPresenter messagePromptPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_message_prompt, -1);
    }

    @Override
    protected void setUpView() {
        messagePromptPresenter = new MessagePromptPresenter(this);
        titleBar.setTitle(R.string.message_prompt);
        titleBar.setRightText(R.string.empty);
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        mDataList = new ArrayList<>();
        recycler.enablePullToRefresh(false);
        recycler.enableLoadMore(true);
        recycler.setRefreshing();
        messagePromptPresenter.getMessagePromptList(pageNum);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_prompt, parent, false);
        return new MessagePromptViewHolder(view, this, mDataList);
    }

    @Override
    public void onRefresh(int action) {
        pageNum++;
        if (pageNum <= totalPages) {
            //下拉刷新
            messagePromptPresenter.getMessagePromptList(pageNum);
        } else {
            recycler.onRefreshCompleted();
            recycler.enableLoadMore(false);
        }
    }

    @Override
    public void setMessagePromptList(List<MessagePromptBean> messagePromptList) {
        if (null != messagePromptList && messagePromptList.size() > 0) {
            findViewById(R.id.empty).setVisibility(View.GONE);
            mDataList.clear();
            mDataList.addAll(messagePromptList);
            adapter.notifyDataSetChanged();
        } else {
            findViewById(R.id.empty).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteComplete(int position) {
        mDataList.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearComplete() {
        mDataList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clickRight() {
        if (mDataList.size() > 0)
            messagePromptPresenter.clearMessagePrompt();
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private class MessagePromptViewHolder extends BaseViewHolder {
        private TextView tvTitle;//标题
        private ImageView ivImage;// 图片
        private TextView tvContent;//内容
        private TextView tvTime;//时间
        private TextView tvOperation;//操作
        private CustomAlertDialog alertDialog;
        private int oldPosition;

        public MessagePromptViewHolder(View itemView, Context context, List<MessagePromptBean> list) {
            super(itemView);
            this.context = context;
            this.list = list;
            ivImage = (ImageView) itemView.findViewById(R.id.image);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvContent = (TextView) itemView.findViewById(R.id.content);
            tvTime = (TextView) itemView.findViewById(R.id.time);
            tvOperation = (TextView) itemView.findViewById(R.id.operation);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    delete();
                    return true;

                }
            });
        }

        @Override
        public void onBindViewHolder(int position) {
            this.oldPosition = position;
            final MessagePromptBean messagePromptBean = mDataList.get(position);
            tvTitle.setText(messagePromptBean.getTitle());
            if (0 == messagePromptBean.getIs_new())
                tvTitle.setSelected(true);
            else
                tvTitle.setSelected(false);
            if (0 == messagePromptBean.getObj_type()) {
                ivImage.setVisibility(View.VISIBLE);
                tvOperation.setText("查看产品详情");
                ImageLoaderUtils.display(context, ivImage, messagePromptBean.getImage(), R.drawable.product_default_icon, R.drawable.product_default_icon);
            } else if (1 == messagePromptBean.getObj_type()) {
                ivImage.setVisibility(View.GONE);
                tvOperation.setText("查看订单详情");
            }
            tvContent.setText(messagePromptBean.getInfo());
            tvTime.setText(messagePromptBean.getTime());
        }

        @Override
        public void onItemClick(View view, int position) {
            Intent intent;
            if (0 == mDataList.get(position).getIs_new()) {
                messagePromptPresenter.readMessagePrompt(mDataList.get(position).getId());
                mDataList.get(position).setIs_new(1);
                adapter.notifyDataSetChanged();
            }
            if ("查看产品详情".equals(tvOperation.getText().toString())) {
                intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id", mDataList.get(position).getObj_id());
                startActivity(intent);

            } else if ("查看订单详情".equals(tvOperation.getText().toString())) {
                intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("id", mDataList.get(position).getObj_id());
                startActivity(intent);
            }
        }

        public void delete() {
            if (alertDialog == null) {
                alertDialog = new CustomAlertDialog(context).builder();
                alertDialog.setTitle("您确定要删除这条消息吗？")
                        .setPositiveButton("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                messagePromptPresenter.deleteMessagePrompt(mDataList.get(oldPosition).getId(), oldPosition);
                                alertDialog.dismiss();
                            }
                        }).setNegativeButton("否", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
            alertDialog.show();
        }
    }
}
