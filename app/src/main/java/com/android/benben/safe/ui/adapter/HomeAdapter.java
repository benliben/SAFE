package com.android.benben.safe.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * Desribe:主界面适配器
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context mContext;//上下文
    private String[] titleStr ;//文字数组
    private int[]mipmapIds;//图片数组

    /*对外公布的构造函数*/
    public HomeAdapter(Context mContext, String[] titleStr, int[] mipmapIds) {
        this.mContext = mContext;
        this.titleStr = titleStr;
        this.mipmapIds = mipmapIds;
    }

    /*创建*/
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.i_home, parent, false));
        return holder;
    }

    /*绑定数据*/
    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, final int position) {
        holder.tv.setText(titleStr[position]);
        holder.iv.setImageResource(mipmapIds[position]);

        /*如果设置了回调 则设置点击事件*/
        if (mOnItemClickListener != null) {
            /*点击*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();//获取位置
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });
            /*长按*/
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();//获取位置
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    /*获取item的个数*/
    @Override
    public int getItemCount() {
        return titleStr.length;
    }

    /*获取Item里面的事项*/
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_home);
            tv = (TextView) itemView.findViewById(R.id.tv_home);
        }
    }

    /**
     * 设置点击事件的回调函数
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
