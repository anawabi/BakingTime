package com.amannawabi.bakingtime.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amannawabi.bakingtime.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    Context mContext;
    ArrayList<String> mData;
    protected static ItemListener mItemClickListener;

    public ListAdapter(Context mContext, ItemListener mItemClickListener, ArrayList<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu_vh, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.setData(mData.get(position), position);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ItemListener {
        void onItemSelected(int pos);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shortDescriptionTv;
        int VHPos;

        public ListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            shortDescriptionTv = itemView.findViewById(R.id.short_description_tv);
        }

        public void setData (String data, int pos){
            shortDescriptionTv.setText(data);
            VHPos = pos;
        }

        @Override
        public void onClick(View v) {
            ListAdapter.mItemClickListener.onItemSelected(VHPos);
        }
    }
}
