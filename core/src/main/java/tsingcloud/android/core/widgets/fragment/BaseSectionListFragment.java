package tsingcloud.android.core.widgets.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tsingcloud.android.core.R;
import tsingcloud.android.core.widgets.fragment.BaseListFragment;
import tsingcloud.android.core.widgets.pull.BaseViewHolder;
import tsingcloud.android.core.widgets.pull.section.SectionData;


/**
 * Created by Stay on 8/3/16.
 * Powered by www.stay4it.com
 */
public abstract class BaseSectionListFragment<T> extends BaseListFragment<SectionData<T>> {
    protected static final int VIEW_TYPE_SECTION_HEADER = 1;
    protected static final int VIEW_TYPE_SECTION_CONTENT = 2;

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SECTION_HEADER) {
            return onCreateSectionHeaderViewHolder(parent);
        }
        return onCreateSectionViewHolder(parent, viewType);
    }

    protected abstract BaseViewHolder onCreateSectionViewHolder(ViewGroup parent, int viewType);

    private BaseViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_pull_to_refresh_section_header, parent, false);
        return new SectionHeaderViewHolder(view);
    }

    @Override
    protected int getItemType(int position) {
        return mDataList.get(position).isHeader ? VIEW_TYPE_SECTION_HEADER : VIEW_TYPE_SECTION_CONTENT;
    }

    @Override
    protected boolean isSectionHeader(int position) {
        return mDataList.get(position).isHeader;
    }

    private class SectionHeaderViewHolder extends BaseViewHolder {
        private final TextView header;

        public SectionHeaderViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.header);
        }

        @Override
        public void onBindViewHolder(int position) {
            header.setText(mDataList.get(position).header);
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

}
