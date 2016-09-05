package com.josealfonsomora.coolappsfacebook.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.josealfonsomora.coolappsfacebook.R;
import com.josealfonsomora.coolappsfacebook.facebookAPI.Data;

import java.util.ArrayList;
import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.MyViewHolder> {

    private List<Data> timeLine;

    public TimeLineAdapter() {
        this.timeLine = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_line_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data data = timeLine.get(position);
        holder.text.setText(data.getStory());
        holder.layout.setGravity(Gravity.END);
        holder.date.setText(data.getCreated_time());
    }

    @Override
    public int getItemCount() {
        return timeLine.size();
    }

    public void clearData() {
        int size = timeLine.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                timeLine.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView date;
        public LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
            layout = (LinearLayout) view.findViewById(R.id.layout);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

    public void addItems(List<Data> timeLineItems) {
        synchronized (timeLine) {
            timeLine.addAll(timeLineItems);
        }
        notifyDataSetChanged();
    }

}