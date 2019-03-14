package com.dream.application.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.dream.application.R;


/**
 * 主要有 三种视图 :
 * 1. 加载更多出错
 * 2. 正在加载更多
 * 3. 没有更多商品
 */

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.layout_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
