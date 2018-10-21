package com.mrxia.meditation.layout.meditation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrxia.meditation.MyApplication;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;
import com.mrxia.meditation.layout.music.MusicPlayActivity;
import com.mrxia.meditation.utils.ActivityUtil;
import com.mrxia.meditation.utils.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import static com.mrxia.meditation.utils.ActivityUtil.dip2px;

public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.VerticalHolder>{
    private Context context;
    private List<Notification> data;

    public ContentRecyclerAdapter(Context context, List<Notification> data) {
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
        return 3;
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

        private void refreshData(final List<Notification> data, final int verposition){
            ViewGroup.LayoutParams layoutParams = hor_recyclerview.getLayoutParams();
            layoutParams.height =dip2px(200, context);
            hor_recyclerview.setLayoutParams(layoutParams);
            hor_recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));
            hor_recyclerview.addOnItemTouchListener(new ItemClickListener(hor_recyclerview, new ItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("path", data.get(position).getResUrl());
                    Intent intent = new Intent();
                    //绑定需要传递的参数
                    intent.putExtras(bundle);
                    intent.setClass(context, MusicPlayActivity.class);
                    context.startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            }));
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
        TextView content;
        TextView title;
        ImageView cardBackground;
        public HorizontalItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.meditation_noti_title);
            content = itemView.findViewById(R.id.meditation_noti_content);
            cardBackground = itemView.findViewById(R.id.meditation_content_cardback);
        }

        public void refreshData(List<Notification> data, int position){
            title.setText(data.get(position).getTitle());
            content.setText(data.get(position).getContent());
            //ImageLoader.getInstance().displayImage(data.get(position).getImgUrl(), cardBackground);
            Picasso
                    .with(context)
                    .load(data.get(position).getImgUrl())
                    .into(cardBackground);
        }
    }
}
