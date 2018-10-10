package com.mrxia.meditation.layout.meditation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Notification;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelRecyclerAdapter extends RecyclerView.Adapter<TravelRecyclerAdapter.MyHolder>{
    private Context context;
    private List<Notification> data;

    public TravelRecyclerAdapter(Context context, List<Notification> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meditation_travel_item, parent, false);
        return new TravelRecyclerAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.refreshData(data, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView title;
        ImageView cardBackground;

        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.travel_noti_title);
            content = itemView.findViewById(R.id.travel_noti_content);
            cardBackground = itemView.findViewById(R.id.travel_content_cardback);
        }

        public void refreshData(List<Notification> data, int position){
            title.setText(data.get(position).getTitle());
            content.setText(data.get(position).getContent());
            //ImageLoader.getInstance().displayImage(data.get(position).getImgUrl(), cardBackground);
            /*Picasso
                    .with(context)
                    .load(data.get(position).getImgUrl())
                    .into(cardBackground);*/
        }
    }
}
