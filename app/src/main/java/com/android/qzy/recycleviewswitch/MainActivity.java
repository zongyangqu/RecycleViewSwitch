package com.android.qzy.recycleviewswitch;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 当前的条目是recyclerView的头布局
    public static final int HEADER_VIEW_ITEM = 0;
    // 一行显示一个
    public static final int RECYCLERVIEW_ITEM_SINGLE = 1;
    // 一行显示两个
    public static final int RECYCLERVIEW_ITEM_DOUBLE = 2;
    private MainAdapter adapter;
    private RecyclerView recycleView;
    private FloatingActionButton fab;
    private GridLayoutManager manager;
    private View headerView;
    private List<Bean> beanList = new ArrayList<Bean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        setListener();
    }

    public void initView(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        manager = new GridLayoutManager(this, 2);
        // 设置布局管理一条数据占用几行，如果是头布局则头布局自己占用一行
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int postion) {
                if (postion == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recycleView.setLayoutManager(manager);
        adapter = new MainAdapter(getActivity());
        headerView = View.inflate(getActivity(), R.layout.item_head, null);
        // 设置当前ViewType
        adapter.setSpanSize(MainActivity.RECYCLERVIEW_ITEM_DOUBLE);
        adapter.addHeadView(headerView);
        recycleView.setAdapter(adapter);
        adapter.setData(beanList);
    }

    public void setListener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRecycleViewList();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    /**
     * 改变RecycleView的显示列数
     */
    private void changeRecycleViewList() {
        if (adapter != null) {
            int spanSize = adapter.getSpanSize();
            // 当前一行显示一列
            if (spanSize == MainActivity.RECYCLERVIEW_ITEM_SINGLE) {
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (adapter.getItemViewType(position) == MainActivity.HEADER_VIEW_ITEM) {
                            return 2;
                        } else {
                            return 1;

                        }
                    }
                });
                adapter.setSpanSize(MainActivity.RECYCLERVIEW_ITEM_DOUBLE);
            }
            // 当前一行显示两列
            else if (spanSize == MainActivity.RECYCLERVIEW_ITEM_DOUBLE) {
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (adapter.getItemViewType(position) == MainActivity.HEADER_VIEW_ITEM) {
                            return 2;
                        } else {
                            return 2;
                        }
                    }
                });
                adapter.setSpanSize(MainActivity.RECYCLERVIEW_ITEM_SINGLE);
            }
            // 第一个参数是动画开始的位置索引
            adapter.notifyItemRangeChanged(2, adapter.getItemCount());
        }
    }

    /**
     * 模拟数据
     */
    public void initData(){
        for(int i = 0;i<20;i++){
            Bean bean = new Bean();
            bean.name = getString(R.string.text_count,i+"");
            beanList.add(bean);
        }
    }

    public Activity getActivity(){
        return this;
    }
}
