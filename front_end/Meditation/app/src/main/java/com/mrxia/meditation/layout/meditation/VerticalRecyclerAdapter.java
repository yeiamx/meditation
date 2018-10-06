package com.mrxia.meditation.layout.meditation;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;

import java.util.List;

import static com.mrxia.meditation.utils.ActivityUtil.dip2px;

public class VerticalRecyclerAdapter extends RecyclerView.Adapter<VerticalRecyclerAdapter.VerticalHolder>{
    private Context context;
    private List<Notification> data;

    public VerticalRecyclerAdapter(Context context, List<Notification> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public VerticalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meditation_content_veritem, parent, false);
        return new VerticalHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalHolder holder, int position) {
        holder.refreshData(data, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<Notification> data){
        this.data = data;
        notifyDataSetChanged();
    }

    class VerticalHolder extends RecyclerView.ViewHolder{
        private RecyclerView hor_recyclerview;

        VerticalHolder(View itemView) {
            super(itemView);
            hor_recyclerview = itemView.findViewById(R.id.meditation_ver_item);
        }

        private void refreshData(List<Notification> data, int position){
            ViewGroup.LayoutParams layoutParams = hor_recyclerview.getLayoutParams();
            layoutParams.height =dip2px(200, context);
            hor_recyclerview.setLayoutParams(layoutParams);
            hor_recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));
            hor_recyclerview.setAdapter(new HorizontalRecyclerViewAdapter(data));
        }
    }

    class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalItemViewHolder>{
        private List<Notification> data;

        public HorizontalRecyclerViewAdapter(List<Notification> data){
            this.data = data;
        }

        @Override
        public HorizontalItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meditation_content_horitem, parent, false);
            return new HorizontalItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HorizontalItemViewHolder holder, int position) {
            holder.refreshData(data, position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
    class HorizontalItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public HorizontalItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.test_text);
        }

        public void refreshData(List<Notification> data, int position){
            textView.setText(data.get(position).getTitle());
        }
    }
}
