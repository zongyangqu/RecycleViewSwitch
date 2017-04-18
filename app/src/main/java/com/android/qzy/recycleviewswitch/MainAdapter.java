package com.android.qzy.recycleviewswitch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/4/18
 *
 * 类描述：
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Bean>beanList;
    private int spanSize;// 当前每行显示几列
    public View headView;

    /**
     * 添加自定义头部
     */
    public void addHeadView(View view) {
        this.headView = view;
    }
    public MainAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Bean>beanList){
        this.beanList = beanList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = null;
        // 头部
        if (viewType == MainActivity.HEADER_VIEW_ITEM) {
            root = headView;
        } else {// 普通条目
            /** 一行显示一条 */
            if (viewType == MainActivity.RECYCLERVIEW_ITEM_SINGLE) {
                root = LayoutInflater.from(context).inflate(R.layout.item_single, parent, false);
            }
            /** 一行显示两条 */
            else {
                root = LayoutInflater.from(context).inflate(R.layout.item_double, parent, false);
            }
        }
        return new ViewHolder(root, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        // 头部
        if (itemViewType == MainActivity.HEADER_VIEW_ITEM) {
            return;
        } else {// 普通条目
            Bean bean = beanList.get(getRealPosition(holder));
            ViewHolder viewHolder = ((ViewHolder) holder);
            if (itemViewType == MainActivity.RECYCLERVIEW_ITEM_DOUBLE) {// 一行两列视图
                viewHolder.tvCountDouble.setText(bean.name);
            } else if (itemViewType == MainActivity.RECYCLERVIEW_ITEM_SINGLE) {// 一行一列视图
                viewHolder.tvCountSingle.setText(bean.name);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(null == beanList || beanList.isEmpty()){
            return 0;
        }else{
            return beanList.size()+1;
        }
    }

    /**
     * 获取数据真实索引
     *
     * @param holder
     * @return
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headView == null ? position : position - 1;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public int getSpanSize() {
        return spanSize;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MainActivity.HEADER_VIEW_ITEM;
        } else {
            /** 一行显示一条 */
            if (spanSize == MainActivity.RECYCLERVIEW_ITEM_SINGLE) {
                return MainActivity.RECYCLERVIEW_ITEM_SINGLE;
                /** 一行显示两条 */
            } else {
                return MainActivity.RECYCLERVIEW_ITEM_DOUBLE;
            }
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        //一行两列视图
        public ImageView ivResourceDouble;
        public TextView tvCountDouble;
        public TextView tvDesDouble;

        //一行一列视图
        public ImageView ivResourceSingle;
        public TextView tvCountSingle;
        public TextView tvDesSingle;

        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            initView(itemView,viewType);
        }
        private void initView(View itemView, int viewType) {
            if(viewType == MainActivity.RECYCLERVIEW_ITEM_DOUBLE){
                ivResourceDouble = (ImageView) itemView.findViewById(R.id.ivResourceDouble);
                tvCountDouble = (TextView) itemView.findViewById(R.id.tvCountDouble);
                tvDesDouble = (TextView) itemView.findViewById(R.id.tvDesDouble);
            }else if(viewType == MainActivity.RECYCLERVIEW_ITEM_SINGLE){
                ivResourceSingle = (ImageView) itemView.findViewById(R.id.ivResourceSingle);
                tvCountSingle = (TextView) itemView.findViewById(R.id.tvCountSingle);
                tvDesSingle = (TextView) itemView.findViewById(R.id.tvDesSingle);
            }
        }
    }
}
