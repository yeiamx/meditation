package com.mrxia.meditation.layout.article;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mrxia.meditation.R;
import com.mrxia.meditation.bean.Journal;
import com.mrxia.meditation.bean.Notification;
import com.mrxia.meditation.layout.meditation.TravelRecyclerAdapter;
import com.mrxia.meditation.utils.HttpUtil;
import com.mrxia.meditation.utils.LoadingView;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mrxia.meditation.MyApplication.urlStarter;

public class JournalFragment extends Fragment {

    private JournalAdapter mAdapter;
    private EditText et;
    private RecyclerView rv;
    private DividerItemDecoration mDividerItemDecoration;
    private ImageButton btn;
    private TextView logan;
    private LoadingView loadingView;
    private List<Notification> data;
    private List<Notification> journals;



    public static JournalFragment newInstance() {
        JournalFragment frag = new JournalFragment();
        return frag;
    }


    private void initData() {
        data = new ArrayList<>();
        loadingView.show();
        getJournals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_article_journal, container, false);

        initView(view);
        registerListener();
        initData();
        return view;
    }

    public void registerListener() {

    }

    public void initView(View view) {
        loadingView = new LoadingView(getActivity(), R.style.CustomDialog);
//        et = view.findViewById(R.id.article_journal_edittext_1);
//        et.setFocusable(false);
//        et.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ArticleJournalActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });
        logan = view.findViewById(R.id.article_journal_logan);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SetoFont.ttf");
        logan.setTypeface(typeface);
        logan.setText("今天心情如何");

        btn = view.findViewById(R.id.article_journal_add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ArticleJournalActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        rv = view.findViewById(R.id.article_journal_rv);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        //rv.addItemDecoration(new ContentFragment.MyDecoration());
        mDividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                new LinearLayoutManager(this.getActivity()).getOrientation());
        rv.addItemDecoration(mDividerItemDecoration);
//        mAdapter = new JournalAdapter(this.getActivity(), this.mDataList);
//        rv.setAdapter(mAdapter);

    }

    private void getJournals(){
        journals = new ArrayList<>();
        String url = urlStarter + "/getNotification";
        String type="journal";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        String paramaters = JSONObject.toJSONString(jsonObject);
        Log.d("xxchu", "using url " + url);

        HttpUtil.postJson_asynch(url, paramaters, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("xxchu", e.getMessage());
                e.printStackTrace();
                if (loadingView!=null) {
                    loadingView.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                Log.d("xxchu", resultStr);
                journals = JSONArray.parseArray(resultStr, Notification.class);

                if (loadingView!=null) {
                    loadingView.dismiss();
                }

                for (int i=0; i<journals.size(); i++){

                    data.add(journals.get(i));
                }

                mAdapter = new JournalAdapter(getActivity(), data);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.setAdapter(mAdapter);
                    }
                });
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("xxchu","into");
        if (resultCode == 4) {
            Notification journal = (Notification) data.getSerializableExtra("newJournal");
//                String newJournal = data.getStringExtra("newJournal");
//                long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
//                SimpleDateFormat format=new SimpleDateFormat("yyyy/M/d");
//                Date d1=new Date(time);
//                String t1=format.format(d1);
//                Notification journal = new Notification("add", "add",newJournal,t1);

                mAdapter.addData(0,journal);
                Log.d("xxchu",this.data.get(0).getContent());
            }

    }
}
