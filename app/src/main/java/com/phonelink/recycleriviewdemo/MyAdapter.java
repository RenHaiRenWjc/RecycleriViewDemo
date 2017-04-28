package com.phonelink.recycleriviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * ClassName:com.phonelink.recycleriviewdemo
 * Description:
 * author:wjc on 2017/4/19 11:39
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.BaseViewHolder> {
    private List<String> dataList;
    LayoutInflater mInflater;
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, List<String> dataList) {
        mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        BaseViewHolder holder = new BaseViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final String text = dataList.get(position);
        holder.mTextView.setText(dataList.get(position));
       // holder.itemView.setOnScrollChangeListener();
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d(TAG, "onClick() called with: item:" + text + " 被点击了");
//                Toast.makeText(v.getContext(), "item:" + text + " 被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogUtils.d(TAG, "onClick() called with: item:" + text + " 被长按了");
//                Toast.makeText(v.getContext(), "item:" + text + " 被长按了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class MyOnScollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    }


}

