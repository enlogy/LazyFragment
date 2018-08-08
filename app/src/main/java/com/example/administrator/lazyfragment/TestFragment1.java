package com.example.administrator.lazyfragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

/**
 * Created by enlogy on 2018/8/8 0008.
 */

public class TestFragment1 extends BaseLazyFragment {

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void initData() {
        Log.d("Test", "initData");
        swipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    protected int onLayoutRes() {
        return R.layout.fragment_blank_fragment2;
    }

    @Override
    protected void initView(View view) {
        Log.d("Test", "initView");
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeRefresh.setRefreshing(true);
    }
}
