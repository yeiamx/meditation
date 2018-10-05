package com.mrxia.meditation.layout.meditation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrxia.meditation.R;

import java.util.List;

public class VerticalRecyclerAdapter extends RecyclerView.Adapter<VerticalRecyclerAdapter.MyHolder>{
    private Context context;
    private List data;

    public VerticalRecyclerAdapter(Context context, List data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meditation_content_veritem, parent, false);
        MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 10;//data.size();
    }

    public void update(List data){
        this.data = data;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;

        MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.test_text);
        }
    }
}
