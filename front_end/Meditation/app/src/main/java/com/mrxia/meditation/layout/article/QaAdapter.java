package com.mrxia.meditation.layout.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Qa;

import java.util.List;

public class QaAdapter extends RecyclerView.Adapter<QaAdapter.QaViewHolder> {

    private Context mContext;
    private  List<Qa> mDataList;

    public QaAdapter(Context context, List<Qa> list){

        this.mContext = context;
        this.mDataList = list;
    }

    @Override
    public QaAdapter.QaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QaAdapter.QaViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_article_qa_item,parent,false));
    }

    @Override
    public void onBindViewHolder(QaAdapter.QaViewHolder holder, final int position) {

        holder.textView.setText(mDataList.get(position).getQuestion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "qa"+position,Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(view.getContext(), ArticleQaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mDataList.get(position).getId() );
                bundle.putString("question", mDataList.get(position).getQuestion());
                bundle.putString("answer", mDataList.get(position).getAnswer());
                intent.putExtras(bundle);
               view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mDataList.size();
    }

    class QaViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public QaViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.article_qa_item_textview);

        }
    }
}