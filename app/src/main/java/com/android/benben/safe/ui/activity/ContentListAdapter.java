package com.android.benben.safe.ui.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.benben.safe.R;

/**
 * Created by LiYuanxiong on 2016/8/8 17:23.
 * Desribe:
 */
public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.MyViewHolder> {

    private Context mContext;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder =new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.i_list,parent,false)) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
