package tsingcloud.android.core.widgets.pull;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by Stay on 1/3/16.
 * Powered by www.stay4it.com
 */
public abstract class BaseViewHolder<E> extends RecyclerView.ViewHolder {
    // 应用上下文
    protected Context context;
    // 数据集合
    protected List<E> list;

    public BaseViewHolder(View itemView) {
        super(itemView);
        setOnClickListener(itemView);
    }

    public BaseViewHolder(View itemView, Context context, List<E> list) {
        super(itemView);
        this.context = context;
        this.list = list;
        setOnClickListener(itemView);
    }

    private void setOnClickListener(View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, getAdapterPosition());
            }
        });
    }

    public abstract void onBindViewHolder(int position);

    public abstract void onItemClick(View view, int position);
}
