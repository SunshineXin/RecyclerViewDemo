package com.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;


    private List<String> list;

    private OnItemClikListener mOnItemClikListener;

    private OnItemLongClickListener mOnItemLongClickListener;

    public RecyclerViewAdapter(List<String> list){
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == TYPE_ITEM){
            View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, null);
            return new ItemViewHolder(view,mOnItemClikListener, mOnItemLongClickListener);
        } else if (viewType == TYPE_FOOTER){
            View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_layout, null);
            return new FooterViewHolder(view,mOnItemClikListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ItemViewHolder){
            String item = getItem(position);
            if(TextUtils.isEmpty(item)){
                return;
            }
            ((ItemViewHolder) viewHolder).mTextView.setText(item);
        }

    }

    @Override
    public int getItemCount() {
        return list.size() + 1;// 多添加了一个footerView
    }

    @Override
    public int getItemViewType(int position){
//        if(position % 2 == 0){
        if((position + 1) == getItemCount()){
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    public String getItem(int position){
        if(position >= list.size()){
            return "";
        }
        return list.get(position);
    }

    /**
     * 设置item的点击事件
     */
    public void setOnItemClickListener(OnItemClikListener listener){
        this.mOnItemClikListener = listener;
    }

    /**
     * 设置item的长点击事件
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.mOnItemLongClickListener = listener;
    }

    /**
     *
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView mTextView;
        private OnItemClikListener onItemClikListener;
        private OnItemLongClickListener onItemLongClickListener;

        public ItemViewHolder(View itemView, OnItemClikListener onItemClikListener, OnItemLongClickListener onItemLongClickListener) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            this.onItemClikListener = onItemClikListener;
            this.onItemLongClickListener = onItemLongClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(onItemClikListener != null){
                onItemClikListener.onItemClick(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(onItemLongClickListener != null){
                onItemLongClickListener.onItemLongClick(view, getAdapterPosition());
            }

            return true;
        }
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private OnItemClikListener onItemClikListener;

        public FooterViewHolder(View itemView, OnItemClikListener onItemClikListener) {
            super(itemView);
            this.onItemClikListener = onItemClikListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClikListener != null){
                onItemClikListener.onItemClick(view, getAdapterPosition());
            }
        }
    }


    /**
     * item的点击事件
     */
    public interface OnItemClikListener {
        void onItemClick(View view, int position);
    }

    /**
     * item的长点击事件
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
