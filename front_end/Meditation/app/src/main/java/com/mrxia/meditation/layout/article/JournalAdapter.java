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
import com.mrxia.meditation.bean.Journal;
import com.mrxia.meditation.bean.Notification;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private Context mContext;
    private  List<Notification> mDataList;

    public JournalAdapter(Context context, List<Notification> list){

        this.mContext = context;
        this.mDataList = list;
    }

    @Override
    public JournalAdapter.JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JournalAdapter.JournalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_article_journal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(JournalAdapter.JournalViewHolder holder, final int position) {
        holder.userName.setText(mDataList.get(position).getTitle());
        holder.time.setText(mDataList.get(position).getTime());
        holder.content.setText(mDataList.get(position).getContent());
        Picasso
                .with(mContext)
                .load(mDataList.get(position).getImgUrl())
                .into(holder.userHead);

 //       holder.textView.setText(mDataList.get(position).getQuestion());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(mContext, "qa"+position,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(view.getContext(), ArticleJournalActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("id", mDataList.get(position).getId() );
//                bundle.putString("question", mDataList.get(position).getQuestion());
//                bundle.putString("answer", mDataList.get(position).getAnswer());
//                intent.putExtras(bundle);
//                view.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return mDataList.size();
    }

    public void addData(int position, Notification notification){
        mDataList.add(position, notification);
        notifyItemInserted(position);
    }

    class JournalViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView userHead;
        private TextView userName;
        private TextView time;
        private TextView content;

        public JournalViewHolder(View itemView) {
            super(itemView);
            userHead = itemView.findViewById(R.id.article_journal_item_iv);
            userName = itemView.findViewById(R.id.article_journal_item_username);
            time = itemView.findViewById(R.id.article_journal_item_time);
            content = itemView.findViewById(R.id.article_journal_item_content);
        }
    }
}
