package com.mrxia.meditation.layout.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Article;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private Context mContext;
    private List<Article> mDataList;

    public ContentAdapter(Context context, List<Article> list){
        this.mContext = context;
        this.mDataList = list;
    }

    @Override
    public ContentAdapter.ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_article_content_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ContentAdapter.ContentViewHolder holder, final int position) {

        //holder.textView.setText("title" + position);

        holder.textView.setText(mDataList.get(position).getTitle());
//        if(position % 2 != 0){
//            holder.imageView.setImageResource(R.drawable.content1);
//
//        }else{
//            holder.imageView.setImageResource(R.drawable.content2);
//        }
        holder.imageView.setImageResource(mDataList.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "click"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ArticleContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mDataList.get(position).getId() );
                bundle.putString("title", mDataList.get(position).getTitle());
                bundle.putString("content", mDataList.get(position).getContent());
                bundle.putInt("image", mDataList.get(position).getImage());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private  TextView textView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.article_content_item_iv);
            textView = itemView.findViewById(R.id.article_content_item_tv);

        }
    }


}
