package com.android.benben.safe.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.benben.safe.R;
import com.android.benben.safe.utils.ToastUrl;

import java.util.List;

/**
 * Created by LiYuanxiong on 2016/7/27 11:33.
 * Desribe:
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mData;
    private String[] titleStr ;
    private int[]mipmapIds;


    public HomeAdapter(Context mContext, String[] titleStr, int[] mipmapIds) {
        this.mContext = mContext;
        this.titleStr = titleStr;
        this.mipmapIds = mipmapIds;
    }

    public HomeAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.i_home, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, final int position) {
        holder.tv.setText(titleStr[position]);
        holder.iv.setBackgroundResource(mipmapIds[position]);
        /*如果设置了回调 则设置点击事件*/
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return titleStr.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_home);
            /**
             * 有问题 需要解决
             */
            tv = (TextView) itemView.findViewById(R.id.tv_home);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
